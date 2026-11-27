package com.example.sportsconnect;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Local database access for joined matches and their group chats.
 * Data stays on this device and survives app restarts.
 */
public final class JoinedGamesStore {

    private static final Map<String, Game> joinedGames = new LinkedHashMap<>();
    private static final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private JoinedGamesStore() {
        // Utility class.
    }

    public interface OperationCallback {
        void onSuccess();
        void onError(Exception exception);
    }

    public interface GamesCallback {
        void onLoaded(List<Game> games);
        void onError(Exception exception);
    }

    public interface MessagesCallback {
        void onChanged(List<String> messages);
        void onError(Exception exception);
    }

    public static boolean isJoined(Game game) {
        return game != null && joinedGames.containsKey(game.getId());
    }

    public static List<Game> getJoinedGames() {
        return new ArrayList<>(joinedGames.values());
    }

    public static Game getGame(String gameId) {
        return joinedGames.get(gameId);
    }

    public static void joinGame(Context context, Game game, OperationCallback callback) {
        if (game == null) {
            callback.onError(new IllegalArgumentException("Game cannot be null."));
            return;
        }

        databaseExecutor.execute(() -> {
            try {
                ChatDao dao = ChatDatabase.getInstance(context).chatDao();
                long insertedId = dao.insertRoom(ChatRoomEntity.fromGame(game));
                if (insertedId == -1) {
                    postError(callback, new IllegalStateException("You already joined this game."));
                    return;
                }

                dao.insertMessage(new ChatMessageEntity(
                        game.getId(),
                        "SportsConnect",
                        "You joined this group chat.",
                        System.currentTimeMillis()));
                joinedGames.put(game.getId(), game);
                postSuccess(callback);
            } catch (Exception exception) {
                postError(callback, exception);
            }
        });
    }

    public static void loadJoinedGames(Context context, GamesCallback callback) {
        databaseExecutor.execute(() -> {
            try {
                List<ChatRoomEntity> rooms = ChatDatabase.getInstance(context)
                        .chatDao()
                        .getJoinedRooms();
                joinedGames.clear();
                for (ChatRoomEntity room : rooms) {
                    joinedGames.put(room.gameId, room.toGame());
                }
                List<Game> games = getJoinedGames();
                mainHandler.post(() -> callback.onLoaded(games));
            } catch (Exception exception) {
                postError(callback, exception);
            }
        });
    }

    public static void loadMessages(Context context, String gameId, MessagesCallback callback) {
        databaseExecutor.execute(() -> {
            try {
                List<ChatMessageEntity> storedMessages = ChatDatabase.getInstance(context)
                        .chatDao()
                        .getMessages(gameId);
                List<String> messages = new ArrayList<>();
                for (ChatMessageEntity message : storedMessages) {
                    messages.add(message.displayText());
                }
                mainHandler.post(() -> callback.onChanged(messages));
            } catch (Exception exception) {
                postError(callback, exception);
            }
        });
    }

    public static void sendMessage(Context context, String gameId, String message,
                                   OperationCallback callback) {
        String trimmedMessage = message == null ? "" : message.trim();
        if (trimmedMessage.isEmpty()) {
            callback.onError(new IllegalArgumentException("Message cannot be empty."));
            return;
        }

        databaseExecutor.execute(() -> {
            try {
                ChatDatabase.getInstance(context).chatDao().insertMessage(new ChatMessageEntity(
                        gameId,
                        "You",
                        trimmedMessage,
                        System.currentTimeMillis()));
                postSuccess(callback);
            } catch (Exception exception) {
                postError(callback, exception);
            }
        });
    }

    private static void postSuccess(OperationCallback callback) {
        mainHandler.post(callback::onSuccess);
    }

    private static void postError(OperationCallback callback, Exception exception) {
        mainHandler.post(() -> callback.onError(exception));
    }

    private static void postError(GamesCallback callback, Exception exception) {
        mainHandler.post(() -> callback.onError(exception));
    }

    private static void postError(MessagesCallback callback, Exception exception) {
        mainHandler.post(() -> callback.onError(exception));
    }
}

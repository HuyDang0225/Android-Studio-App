package com.example.sportsconnect;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds the games and messages joined during this app session.
 * Replace this with Firestore once authentication and a Firebase project are configured.
 */
public final class JoinedGamesStore {

    private static final Map<String, Game> joinedGames = new LinkedHashMap<>();
    private static final Map<String, List<String>> messagesByGame = new LinkedHashMap<>();

    private JoinedGamesStore() {
        // Utility class.
    }

    public static boolean joinGame(Game game) {
        if (game == null || joinedGames.containsKey(game.getId())) {
            return false;
        }

        joinedGames.put(game.getId(), game);
        List<String> messages = new ArrayList<>();
        messages.add("You joined the group chat for \"" + game.getTitle() + "\".");
        messagesByGame.put(game.getId(), messages);
        return true;
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

    public static List<String> getMessages(String gameId) {
        List<String> messages = messagesByGame.get(gameId);
        return messages == null ? new ArrayList<>() : new ArrayList<>(messages);
    }

    public static void addMessage(String gameId, String message) {
        List<String> messages = messagesByGame.get(gameId);
        if (messages != null && message != null && !message.trim().isEmpty()) {
            messages.add("You: " + message.trim());
        }
    }
}

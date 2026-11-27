package com.example.sportsconnect;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "chat_rooms")
public class ChatRoomEntity {
    @PrimaryKey
    @NonNull
    public String gameId;
    public String sport;
    public String title;
    public String locationTime;
    public int currentPlayers;
    public int maxPlayers;
    public long joinedAt;

    public Game toGame() {
        return new Game(gameId, sport, title, locationTime, currentPlayers, maxPlayers);
    }

    public static ChatRoomEntity fromGame(Game game) {
        ChatRoomEntity room = new ChatRoomEntity();
        room.gameId = game.getId();
        room.sport = game.getSport();
        room.title = game.getTitle();
        room.locationTime = game.getLocationTime();
        room.currentPlayers = game.getCurrentPlayers();
        room.maxPlayers = game.getMaxPlayers();
        room.joinedAt = System.currentTimeMillis();
        return room;
    }
}

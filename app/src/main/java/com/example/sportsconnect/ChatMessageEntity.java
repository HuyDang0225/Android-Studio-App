package com.example.sportsconnect;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "chat_messages",
        foreignKeys = @ForeignKey(
                entity = ChatRoomEntity.class,
                parentColumns = "gameId",
                childColumns = "gameId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("gameId")}
)
public class ChatMessageEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String gameId;
    public String senderName;
    public String text;
    public long createdAt;

    public ChatMessageEntity(String gameId, String senderName, String text, long createdAt) {
        this.gameId = gameId;
        this.senderName = senderName;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String displayText() {
        return senderName + ": " + text;
    }
}

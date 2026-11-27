package com.example.sportsconnect;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ChatRoomEntity.class, ChatMessageEntity.class}, version = 1, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {
    private static volatile ChatDatabase instance;

    public abstract ChatDao chatDao();

    public static ChatDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (ChatDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ChatDatabase.class,
                                    "sportsconnect.db")
                            .build();
                }
            }
        }
        return instance;
    }
}

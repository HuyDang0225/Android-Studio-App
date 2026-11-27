package com.example.sportsconnect;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM chat_rooms ORDER BY joinedAt DESC")
    List<ChatRoomEntity> getJoinedRooms();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertRoom(ChatRoomEntity room);

    @Query("SELECT * FROM chat_messages WHERE gameId = :gameId ORDER BY createdAt ASC, id ASC")
    List<ChatMessageEntity> getMessages(String gameId);

    @Insert
    long insertMessage(ChatMessageEntity message);
}

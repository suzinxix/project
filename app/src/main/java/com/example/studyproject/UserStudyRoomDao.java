package com.example.studyproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserStudyRoomDao {
    @Insert
    void insert(UserStudyRoom userStudyRoom);

    @Update
    void update(UserStudyRoom userStudyRoom);

    @Delete
    void delete(UserStudyRoom userStudyRoom);

    @Query("DELETE FROM user_room")
    void deleteAllUserRoom();

    //@Query("SELECT * FROM user_room ORDER BY priority DESC")
    @Query("SELECT * FROM user_room")
    LiveData<List<UserStudyRoom>> getAllUserStudyRoom();
}

package com.example.studyproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_room")
public class UserStudyRoom {
    @PrimaryKey(autoGenerate = true)
    private int id;

    // 스터디룸 이름
    @ColumnInfo(name="study_room")
    public String roomtitle;

    // 스터디룸 꿀
    @ColumnInfo(name="study_ggul")
    public int roomggul;

    // 시작날짜
    @ColumnInfo(name="study_day")
    public int roomstartday;

    public UserStudyRoom(String roomtitle, int roomggul, int roomstartday){
        this.roomtitle = roomtitle;
        this.roomggul = roomggul;
        this.roomstartday = roomstartday;
    }

    // getter & setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomtitle() {
        return roomtitle;
    }

    public void setRoomtitle(String roomtitle) {
        this.roomtitle = roomtitle;
    }

    public int getRoomggul() {
        return roomggul;
    }

    public void setRoomggul(int roomggul) {
        this.roomggul = roomggul;
    }

    public int getRoomstartday() {
        return roomstartday;
    }

    public void setRoomstartday(int roomstartday) {
        this.roomstartday = roomstartday;
    }
}

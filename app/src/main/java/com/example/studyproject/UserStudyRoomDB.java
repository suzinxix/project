package com.example.studyproject;

public class UserStudyRoomDB {
    public String room_name;
    public int ggul;
    public int daystart;

    public UserStudyRoomDB(String room_name, int ggul, int daystart) {
        this.room_name = room_name;
        this.ggul = ggul;
        this.daystart = daystart;
    }

    public String getStudyroomname() {
        return room_name;
    }
    public void setStudyroomname(String room_name) {
        this.room_name = room_name;
    }

    public int getGgul() {return ggul;}
    public void setGgul(int ggul) {this.ggul = ggul;}

    public int getDaystart() {return daystart;}
    public void setDaystart(int daystart) {this.daystart=daystart;}
}

package com.example.studyproject;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MakeRoomDB {
    public String roomname;
    public String roomcategory;
    public String roominfo;
    public String roomauth;
    public Map<String, Boolean> stars = new HashMap<>();

    public MakeRoomDB(){

    }

    public MakeRoomDB(String roomname, String roomcategory, String roominfo, String roomauth){
        this.roomname = roomname;
        this.roomcategory = roomcategory;
        this.roominfo = roominfo;
        this.roomauth = roomauth;
    }
/*
    public String getRoomname() {
        return roomname;
    }

    public void setRoomName(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomcategory() {
        return roomcategory;
    }

    public void setRoomcategory(String roomcategory) {
        this.roomcategory = roomcategory;
    }

    public String getRoominfo() {
        return roominfo;
    }

    public void setRoominfo(String roominfo) {
        this.roominfo = roominfo;
    }

    public String getRoomauth() {
        return roomauth;
    }

    public void setRoomauth(String roomauth) {
        this.roomauth = roomauth;
    }

    @Override
    public String toString() {
        return "MakeRoomDB{" +
                "roomname='" + roomname + '\'' +
                ", roomcategory='" + roomcategory + '\'' +
                ", roominfo='" + roominfo + '\'' +
                ", roomauth='" + roomauth + '\'' +
                '}';
    }

  */
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("roomname", roomname);
        result.put("roomcategory", roomcategory);
        result.put("roominfo", roominfo);
        result.put("roomauth", roomauth);
        return result;
    }
}

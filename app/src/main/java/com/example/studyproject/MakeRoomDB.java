package com.example.studyproject;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MakeRoomDB {
    public String roomname;
    public String roomcategory;
    public String roominfo;
    public String roomauth;
    public String roomperson;
    public boolean roomday;
    public boolean roomtime;
    public boolean roomlock;

    public Map<String, Boolean> stars = new HashMap<>();

    public MakeRoomDB(){

    }

    public MakeRoomDB(String roomname, String roomcategory, String roominfo, String roomauth, String roomperson, boolean roomday,boolean roomtime, boolean roomlock){
        this.roomname = roomname;
        this.roomcategory = roomcategory;
        this.roominfo = roominfo;
        this.roomauth = roomauth;
        this.roomperson = roomperson;
        this.roomday = roomday;
        this.roomtime = roomtime;
        this.roomlock = roomlock;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
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

    public String getRoomauth() { return roomauth;
    }

    public void setRoomauth(String roomauth) { this.roomauth = roomauth;
    }

    public String getRoomperson() {return roomperson;}

    public void setRoomperson(String roomperson) {this.roomperson = roomperson;}

    public boolean getRoomday() {return roomday;}

    public void setRoomday() {this.roomday = roomday;}

    public boolean getRoomtime() {return roomtime;}

    public void setRoomtime() {this.roomtime = roomtime;}

    public boolean getRoomlock() {return roomlock;}

    public void setRoomlock() {this.roomlock = roomlock;}
/*
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
        result.put("roomperson", roomperson);
        result.put("roomtime", roomtime);
        result.put("roomday", roomday);
        result.put("roomlock", roomlock);
        return result;
    }
}

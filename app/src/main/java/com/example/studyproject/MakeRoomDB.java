package com.example.studyproject;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class MakeRoomDB {
    public String roomname;
    public String roomcategory;
    public String roominfo;
    public String roomauth;
    public String roomperson;
    public boolean roomday;
    public List<Integer> roomwhen;
    public boolean roomtime;
    public boolean roomlock;
    public Integer roomhow;
    public String roomtime1;
    public String roomtime2;
    public List<String> roommember;
    public List<String> roomToDo;
    public Date roomdate;
    public long roomhoney = 0; // 꿀
    public long roomcurperson = 1; // 현재 인원 수
    public long roomneghoney = 0; // 정렬 꿀
    public long roomnegcurperson = -1; // 정렬 현재 인원수

    public Map<String, Boolean> stars = new HashMap<>();

    public MakeRoomDB(){

    }

    public MakeRoomDB(String roomname, String roomcategory, String roominfo, String roomauth,
                      String roomperson, boolean roomday, List<Integer> roomWhen, boolean roomtime,
                      boolean roomlock, Integer roomhow, String time1, String time2, List<String> roommember, Date roomdate){
        this.roomname = roomname; // 방 이름
        this.roomcategory = roomcategory; // 카테고리 (습관/공부/취미/운동/기타)
        this.roominfo = roominfo; // 소개글
        this.roomauth = roomauth; // 인증 횟수

        this.roomperson = roomperson; // 최대 인원
        this.roomday = roomday; // 인증 요일 사용 여부
        this.roomwhen = roomWhen; // 인증 요일
        this.roomtime = roomtime; // 인증 시간 사용 여부

        this.roomlock = roomlock; // 비공개 여부
        this.roomhow = roomhow; // 인증 방식 선택 (횟수 / 시간)
        this.roomtime1 = time1; // 인증 시작 시간
        this.roomtime2 = time2; // 인증 종료 시간

        this.roommember = roommember; // 스터디원

        this.roomdate = roomdate; // 개설 날짜
    }


    public Date getRoomdate() {
        return roomdate;
    }

    public void setRoomdate(Date roomdate) {
        this.roomdate = roomdate;
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

    public String getRoomauth() { return roomauth; }

    public void setRoomauth(String roomauth) { this.roomauth = roomauth; }

    public String getRoomperson() {return roomperson;}

    public void setRoomperson(String roomperson) {this.roomperson = roomperson;}

    public boolean getRoomday() {return roomday;}

    public void setRoomday() {this.roomday = roomday;}

    public List<Integer> getRoomwhen() {return roomwhen;}

    public void setRoomwhen() {this.roomwhen = roomwhen;}

    public boolean getRoomtime() {return roomtime;}

    public void setRoomtime() {this.roomtime = roomtime;}

    public boolean getRoomlock() {return roomlock;}

    public void setRoomlock() {this.roomlock = roomlock;}

    public Integer getRoomhow() {return roomhow;}

    public void setRoomhow() {this.roomhow = roomhow;}

    public String getRoomtime1() {return roomtime1;}

    public void setRoomtime1() {this.roomtime1 =  roomtime1;}

    public String getRoomtime2() {return roomtime2;}

    public void setRoomtime2() {this.roomtime2 =  roomtime2;}

    public List<String> getRoommember() {
        return roommember;
    }

    public void setRoommember(List<String> roommember) {
        this.roommember = roommember;
    }

    public long getRoomhoney() {
        return roomhoney;
    }

    public void setRoomhoney(long honey) {
        this.roomhoney=honey;
        setRoomneghoney(-honey);
    }
    public long getRoomcurperson() {
        return roomcurperson;

    }

    public void setRoomcurperson(long cp) {
        this.roomcurperson=cp;
        setRoomnegcurperson(-cp);
    }
    public long getRoomnegmhoney() {
        return roomneghoney;
    }

    public void setRoomneghoney(long honey) {
        this.roomneghoney=honey;
    }
    public long getRoomnegcurperson() {
        return roomnegcurperson;
    }

    public void setRoomnegcurperson(long cp) {
        this.roomnegcurperson=cp;
    }



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

        public MakeRoomDB(String roomname, String roomcategory, String roominfo, String roomauth,
                      String roomperson, boolean roomday, int[] roomWhen, boolean roomtime,
                      boolean roomlock, int roomHow, String time1, String time2){
        this.roomname = roomname; // 방 이름
        this.roomcategory = roomcategory; // 카테고리 (습관/공부/취미/운동/기타)
        this.roominfo = roominfo; // 소개글
        this.roomauth = roomauth; // 인증 횟수

        this.roomperson = roomperson; // 최대 인원
        this.roomday = roomday; // 인증 요일 사용 여부
        this.roomwhen = roomWhen; // 인증 요일
        this.roomtime = roomtime; // 인증 시간 사용 여부

        this.roomlock = roomlock; // 비공개 여부
        this.roomhow = roomHow; // 인증 방식 선택 (횟수 / 시간)
        this.roomtime1 = time1; // 인증 시작 시간
        this.roomtime2 = time2; // 인증 종료 시간
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
        result.put("roomday", roomday);
        result.put("roomwhen", roomwhen);
        result.put("roomtime", roomtime);

        result.put("roomlock", roomlock);
        result.put("roomhow", roomhow);
        result.put("roomtime1", roomtime1);
        result.put("roomtime2", roomtime2);

        result.put("roommember", roommember);
        result.put("roomtodo", roomToDo);
        result.put("roomdate", roomdate);

        result.put("roomhoney", roomhoney); // 꿀
        result.put("roomcurperson", roomcurperson); // 현재 인원
        result.put("roomneghoney", -1*roomhoney); // 꿀(정렬용 음수)
        result.put("roomnegcurperson", -1*roomcurperson); // 현재 인원(정렬용 음수)
        return result;
    }
}
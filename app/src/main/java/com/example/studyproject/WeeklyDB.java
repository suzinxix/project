package com.example.studyproject;

import android.widget.Button;

import com.google.firebase.database.Exclude;

public class WeeklyDB {
//    public int week;
    public String todo;
//    public String todo2;
    private String mKey;
//    public String timer;

    public WeeklyDB () {

    }

    public WeeklyDB (String todo) { //, String camera, String timer
        this.todo = todo;
    }

    public String getTodo() { return todo; }
    public void setTodo(String todo) { this.todo = todo; }

    @Exclude
    public String getKey() { return mKey; }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}

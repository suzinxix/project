package com.example.studyproject;

import android.widget.Button;

public class WeeklyDB {
    public int week;
    public String todo;

    public WeeklyDB () {

    }

    public WeeklyDB (int week, String todo, String camera, String timer) {
        this.week = week;
        this.todo = todo;
    }

    public int getWeek() { return week; }
    public void setWeek(int week) { this.week = week; }

    public String getTodo() { return todo; }
    public void setTodo(String todo) { this.todo = todo; }
}

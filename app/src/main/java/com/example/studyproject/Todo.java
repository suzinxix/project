package com.example.studyproject;

public class Todo {
    int _id;
    String todo;

    public Todo(int _id, String todo){
        this._id = _id;
        this.todo = todo;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}

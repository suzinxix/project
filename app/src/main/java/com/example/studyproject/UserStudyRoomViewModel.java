package com.example.studyproject;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserStudyRoomViewModel extends AndroidViewModel {
    private UserStudyRoomRepository repository;
    private LiveData<List<UserStudyRoom>> allUserStudyRooms;
    public UserStudyRoomViewModel(@NonNull Application application){
        super(application);
        repository = new UserStudyRoomRepository(application);
        allUserStudyRooms = repository.getAllUserStudyRooms();
    }

    public void insert(UserStudyRoom userStudyRoom){
        repository.insert(userStudyRoom);
    }

    public void update(UserStudyRoom userStudyRoom){
        repository.update(userStudyRoom);
    }

    public void delete(UserStudyRoom userStudyRoom){
        repository.delete(userStudyRoom);
    }

    public void deleteAllUserStudyRooms() {
        repository.deleteAllUserStudyRooms();
    }

    public LiveData<List<UserStudyRoom>> getAllUserStudyRooms() {
        return allUserStudyRooms;
    }
}

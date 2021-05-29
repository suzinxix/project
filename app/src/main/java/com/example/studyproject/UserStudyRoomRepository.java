package com.example.studyproject;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserStudyRoomRepository {
    private UserStudyRoomDao userStudyRoomDao;
    private LiveData<List<UserStudyRoom>> allUserStudyRooms;

    public UserStudyRoomRepository(Application application) {
        UserStudyRoomDataBase dataBase = UserStudyRoomDataBase.getInstance(application);
        userStudyRoomDao = dataBase.userStudyRoomDao();
        allUserStudyRooms = userStudyRoomDao.getAllUserStudyRoom();
    }

    public void insert(UserStudyRoom userStudyRoom){
        new InsertUserStudyRoomAsyncTask(userStudyRoomDao).execute(userStudyRoom);
    }

    public void update(UserStudyRoom userStudyRoom){
        new UpdateUserStudyRoomAsyncTask(userStudyRoomDao).execute(userStudyRoom);
    }

    public void delete(UserStudyRoom userStudyRoom){
        new DeleteUserStudyRoomAsyncTask(userStudyRoomDao).execute(userStudyRoom);
    }

    public void deleteAllUserStudyRooms(){
        new DeleteAllUserStudyRoomsUserStudyRoomAsyncTask(userStudyRoomDao).execute();
    }

    public LiveData<List<UserStudyRoom>> getAllUserStudyRooms(){
        return allUserStudyRooms;
    }

    private static class InsertUserStudyRoomAsyncTask extends AsyncTask<UserStudyRoom, Void, Void>{
        private UserStudyRoomDao userStudyRoomDao;

        private InsertUserStudyRoomAsyncTask(UserStudyRoomDao userStudyRoomDao){
            this.userStudyRoomDao = userStudyRoomDao;
        }

        @Override
        protected Void doInBackground(UserStudyRoom... userStudyRooms) {
            userStudyRoomDao.insert(userStudyRooms[0]);
            return null;
        }
    }

    private static class UpdateUserStudyRoomAsyncTask extends AsyncTask<UserStudyRoom, Void, Void>{
        private UserStudyRoomDao userStudyRoomDao;

        private UpdateUserStudyRoomAsyncTask(UserStudyRoomDao userStudyRoomDao){
            this.userStudyRoomDao = userStudyRoomDao;
        }

        @Override
        protected Void doInBackground(UserStudyRoom... userStudyRooms) {
            userStudyRoomDao.insert(userStudyRooms[0]);
            return null;
        }
    }

    private static class DeleteUserStudyRoomAsyncTask extends AsyncTask<UserStudyRoom, Void, Void>{
        private UserStudyRoomDao userStudyRoomDao;

        private DeleteUserStudyRoomAsyncTask(UserStudyRoomDao userStudyRoomDao){
            this.userStudyRoomDao = userStudyRoomDao;
        }

        @Override
        protected Void doInBackground(UserStudyRoom... userStudyRooms) {
            userStudyRoomDao.insert(userStudyRooms[0]);
            return null;
        }
    }

    private static class DeleteAllUserStudyRoomsUserStudyRoomAsyncTask extends AsyncTask<UserStudyRoom, Void, Void>{
        private UserStudyRoomDao userStudyRoomDao;

        private DeleteAllUserStudyRoomsUserStudyRoomAsyncTask(UserStudyRoomDao userStudyRoomDao){
            this.userStudyRoomDao = userStudyRoomDao;
        }

        @Override
        protected Void doInBackground(UserStudyRoom... userStudyRooms) {
            userStudyRoomDao.insert(userStudyRooms[0]);
            return null;
        }
    }
}


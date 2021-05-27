package com.example.studyproject;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserStudyRoom.class}, version=1)
public abstract class UserStudyRoomDataBase extends RoomDatabase {
    private static UserStudyRoomDataBase instance;
    public abstract UserStudyRoomDao userStudyRoomDao();

    public static synchronized UserStudyRoomDataBase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserStudyRoomDataBase.class, "userStudyRoom_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserStudyRoomDao userStudyRoomDao;

        private PopulateDbAsyncTask(UserStudyRoomDataBase db) {
            userStudyRoomDao = db.userStudyRoomDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userStudyRoomDao.insert(new UserStudyRoom("가입한 스터디룸", 0, 0));
            return null;
        }
    }
}

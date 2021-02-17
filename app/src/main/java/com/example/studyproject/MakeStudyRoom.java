package com.example.studyproject;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MakeStudyRoom extends AppCompatActivity {

    //여럽운 이거 실패한 자바파일이에여 MakeRoom파일로 교체했슴다
    /*
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private Button bt_make;
    public String g_name;
    public String g_category;
    public String g_intro;
    public String g_auth;
    public EditText studyname, studycategory, studyintro, studyauth;
    //public boolean g_privacy;
    public Map<String, Boolean> stars = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_makestudygroup);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        bt_make = (Button)findViewById(R.id.makegroup);
        studyname = (EditText)findViewById(R.id.studyname);
        studycategory = (EditText)findViewById(R.id.studycategory);
        studyintro = (EditText)findViewById(R.id.studyintro);
        studyauth = (EditText)findViewById(R.id.studyauth);

        bt_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getRoomName = studyname.getText().toString();
                String getRoomCategory = studycategory.getText().toString();
                String getRoomIntro = studyintro.getText().toString();
                String getRoomAuth = studyauth.getText().toString();

                //hashmap 만들기
                HashMap result = new HashMap<>();
                result.put("name", getRoomName);
                result.put("category", getRoomCategory);
                result.put("info", getRoomIntro);
                result.put("aWeek", getRoomAuth);

                makenewstudyroom("1", getRoomName, getRoomCategory, getRoomIntro, getRoomAuth);

            }
        });
    }

    public MakeStudyRoom() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public MakeStudyRoom(String g_name, String g_category, String g_intro, String g_auth) {
        this.g_name = g_name;
        this.g_category = g_category;
        this.g_intro = g_intro;
        this.g_auth = g_auth;
    }

    public String getRoomName() {
        return g_name;
    }

    public String getRoomCategory() {
        return g_category;
    }

    public String getRoomIntro() {
        return g_intro;
    }

    public String getRoomAuth() {
        return g_auth;
    }

    // Json
    @Override
    public String toString() {
        return "StudyRoom{" +
                "Name='" + g_name + '\'' +
                ", Category='" + g_category + '\'' +
                ", Info='" + g_intro + '\'' +
                ", aWeek='" + g_auth + '\'' +
                '}';
    }

    // 스터디룸 생성 함수
    private void makenewstudyroom(String roomId, String g_name, String g_category, String g_intro, String g_auth) {
        //String key = mDatabase.child("posts").push().getKey();
        MakeStudyRoom studyroom = new MakeStudyRoom(g_name, g_category, g_intro, g_auth);

        mDatabase.child("studyroom").child(roomId).setValue(studyroom)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(MakeStudyRoom.this, "스터디룸을 개설하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(MakeStudyRoom.this, "스터디룸 개설을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void readRoom(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(MakeStudyRoom.class) != null){
                    MakeStudyRoom post = dataSnapshot.getValue(MakeStudyRoom.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(MakeStudyRoom.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }*/
}

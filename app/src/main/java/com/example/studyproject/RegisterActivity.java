package com.example.studyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_email, et_pw, et_name, et_nickname;
    private Button button3;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDBReference = firebaseDatabase.getReference();
    private static final String TAG = "RegisterActivity";
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    User user = null;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        et_email =(EditText)findViewById(R.id.et_email);
        et_pw = (EditText)findViewById(R.id.et_pw);
        et_name = (EditText)findViewById(R.id.et_name);
        et_nickname = (EditText)findViewById(R.id.et_nickname);

        button3 = (Button)findViewById(R.id.button3);

        firebaseAuth = FirebaseAuth.getInstance();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = et_email.getText().toString().trim();
                final String pw = et_pw.getText().toString().trim();
                final String name = et_name.getText().toString().trim();
                final String nickname = et_nickname.getText().toString().trim();




                firebaseAuth.createUserWithEmailAndPassword(id, pw)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // 가입성공시
                                if(task.isSuccessful()) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    // 해쉬맴 테이블을 파이어베이스 데이터베이스에 저장

                                    String getUserName = et_name.getText().toString();
                                    String getUserEmail = et_email.getText().toString();
                                    String getUserNick = et_nickname.getText().toString();


                                    String[] userId = {"1", "2", "3", "4", "5","6"};
                                    //hashmap 만들기
                                    HashMap result = new HashMap<>();
                                    result.put("name", getUserName);
                                    result.put("email", getUserEmail);
                                    result.put("nickname", getUserNick);

                                    writeNewUser(userId[i++],getUserName,getUserEmail, getUserNick);

                                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                    user.updateProfile(profileChangeRequest)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "User profile updated.");
                                                    }
                                                }
                                            });
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else{
                                    Toast.makeText(RegisterActivity.this, "회원가입을 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
        });

    }

    private void writeNewUser(String userId, String name, String email, String nickname) {
        User user = new User(name, email, nickname);

        mDBReference.child("users").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(RegisterActivity.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(RegisterActivity.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
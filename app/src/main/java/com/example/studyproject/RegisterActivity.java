package com.example.studyproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_id, et_pw, et_name;
    private Button button3;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_id =(EditText)findViewById(R.id.et_id);
        et_pw = (EditText)findViewById(R.id.et_pw);
        et_name = (EditText)findViewById(R.id.et_name);

        firebaseAuth = FirebaseAuth.getInstance();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String id = et_id.getText().toString().trim();
                final String pw = et_pw.getText().toString().trim();
                final String name = et_name.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(id, pw)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
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
}
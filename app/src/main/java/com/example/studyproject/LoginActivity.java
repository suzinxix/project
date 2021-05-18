package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private Button bt_login, bt_find, bt_join;
    private EditText editTextId, editTextPw;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance(); // firebase 인스턴스
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        bt_login = (Button)findViewById(R.id.buttonLogin);
        bt_find = (Button)findViewById(R.id.buttonFind);
        bt_join = (Button)findViewById(R.id.buttonJoin);
        editTextId = (EditText)findViewById(R.id.editTextId);
        editTextPw = (EditText)findViewById(R.id.editTextPw);

        //회원가입
        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        // 로그인 버튼
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentLogin = new Intent(Login.this, MyStudyRoom.class); //수정
//                startActivity(intentLogin);
                String id = editTextId.getText().toString().trim();
                String pw = editTextPw.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(id, pw)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    //finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        //찾기
       // bt_find.setOnClickListener(new View.OnClickListener() {

       // });
//
//        bt_join = (Button)findViewById(R.id.buttonJoin);
//        bt_join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentJoin = new Intent(Main.this, .class);
//                startActivity(intentJoin);
//            }
//        });

        // 자동 로그인
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
            // or do some other stuff that you want to do
        }
    }

}

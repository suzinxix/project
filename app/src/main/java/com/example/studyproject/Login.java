package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button bt_login, bt_find, bt_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_login = (Button)findViewById(R.id.buttonLogin);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentLogin = new Intent(Login.this, MyStudyRoom.class); //수정
//                startActivity(intentLogin);
            }
        });

        bt_find = (Button)findViewById(R.id.buttonFind);
        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentLogin = new Intent(Login.this, MyStudy.class); //수정
//                startActivity(intentLogin);
            }
        });
//
//        bt_join = (Button)findViewById(R.id.buttonJoin);
//        bt_join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentJoin = new Intent(Main.this, .class);
//                startActivity(intentJoin);
//            }
//        });
    }
}

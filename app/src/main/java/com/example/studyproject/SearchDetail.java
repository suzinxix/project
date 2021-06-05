package com.example.studyproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchDetail extends AppCompatActivity {
    //private ArrayList<UserStudyRoomDB> dataholder;
    //private MyRoomFragmentAdapter myadapter;
    //RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;
    //ArrayList<UserStudyRoomDB> dataholder;

    MakeRoomDB model;
    TextView Roomname, Roominfo, Roomperson, Roomdate;
    ImageView Roompic;
    Button bt;
    public SearchDetail(){
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Roomname=findViewById(R.id.study_title);
        Roominfo=findViewById(R.id.study_info);
        Roomdate=findViewById(R.id.study_make);
        Roomperson=findViewById(R.id.study_member);
        Roompic=findViewById(R.id.study_picture);

        // SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //String getTime = simpleDate.format(mDate);

        String name = getIntent().getStringExtra("Roomname");
        String info = getIntent().getStringExtra("Roominfo");
        String date = getIntent().getStringExtra("Roomdate");
        String person = getIntent().getStringExtra("Roomperson");

        Roomname.setText(name);
        Roominfo.setText(info);
        Roomdate.setText("개설 날짜         " + date);
        Roomperson.setText("멤버                " + person + "명");
        Roompic.setImageResource(R.drawable.book);

        //roomcate=findViewById(R.id.)

        /* 카테고리 별 이미지 입히고 싶은데 실패함
        if(model.roomcategory=="공부")
            Roompic.setImageResource(R.drawable.book);
        else if(model.roomcategory=="습관")
            Roompic.setImageResource(R.drawable.habbit);
        else if(model.roomcategory=="운동")
            Roompic.setImageResource(R.drawable.exercise);
        else if(model.roomcategory=="취미")
            Roompic.setImageResource(R.drawable.hobby);
        else if(model.roomcategory=="기타")
            Roompic.setImageResource(R.drawable.study);
         */

        //mDatabase = FirebaseDatabase.getInstance().getReference();
        bt = findViewById(R.id.bt_apply);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스터디룸 데이터 넘기기
                AlertDialog.Builder dlg = new AlertDialog.Builder(SearchDetail.this);
                dlg.setTitle("Message") //제목
                        .setMessage("선택하신 스터디룸에 가입하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 파이어베이스에 가입한 사용자 멤버 정보 추가
                                FirebaseDatabase  database = FirebaseDatabase.getInstance();
                                DatabaseReference mDatabaseRef = database.getReference("study_rooms/" +name +"/"); // 해당 스터디룸 찾아 들어가기

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저 정보 가져오기
                                String uid = user != null ? user.getUid() : null; // 로그인한 유저의 고유 uid 가져오기

                                mDatabaseRef.child("member").child("name").setValue(uid); // 사용자 uid 삽입
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = dlg.create();
                dlg.show();
            }
        });
    }
}

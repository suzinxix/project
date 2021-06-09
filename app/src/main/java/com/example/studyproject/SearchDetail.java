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
    TextView Roomname, Roominfo, Roomperson, Roomdate, Roomcate, Roomhow, Roomauth, Roomday;
    ImageView Roompic;
    Button bt; String temp, z1="", z2="", z3="", z4="";

    String roomcategory; long roomhoney; long curperson; // 카테고리,  꿀, 현재인원
    int roomauth, roomhow; String roomtime1, roomtime2; // 방식 (횟수/시간)
    Boolean roomday; String roomwhen; // 요일 설정

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
        Roomcate=findViewById(R.id.study_cate);
        Roomhow=findViewById(R.id.study_how);
        Roomauth=findViewById(R.id.study_auth);
        Roomday=findViewById(R.id.study_day);
        // SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //String getTime = simpleDate.format(mDate);

        String name = getIntent().getStringExtra("Roomname");
        String info = getIntent().getStringExtra("Roominfo");
        String date = getIntent().getStringExtra("Roomdate");
        String person = getIntent().getStringExtra("Roomperson");
        curperson = getIntent().getLongExtra("Roomcurperson", 0);

        temp = getIntent().getStringExtra("Roomauth");
        roomauth = 0;
        if(!temp.equals("")) roomauth = Integer.parseInt(temp);

        roomhoney = getIntent().getLongExtra("Roomhoney",0);
        roomhow = getIntent().getIntExtra("Roomhow",0);

        roomcategory = getIntent().getStringExtra("Roomcategory");
        roomtime1 = getIntent().getStringExtra("Roomtime1");
        roomtime2 = getIntent().getStringExtra("Roomtime2");
        roomday = getIntent().getBooleanExtra("Roomday", false);
        roomwhen = getIntent().getStringExtra("Roomwhen");


        Roomname.setText(name);
        Roominfo.setText(info);
        Roomdate.setText("개설 날짜         " + date);
        Roomperson.setText("멤버                  " + curperson +"/" +person + "명");
        Roompic.setImageResource(R.drawable.book);

        Roomcate.setText("카테고리          "+roomcategory);
        String roomHowText=""; int h1, m1, h2, m2;
        if(roomhow==0){
            roomHowText="횟수";
            Roomauth.setText("인증 횟수         일주일에  "+roomauth+"회");
        }
        else if(roomhow==1) {
            roomHowText = "시간";
            h1 = Integer.parseInt(roomtime1.substring(0,2));
            m1 = Integer.parseInt(roomtime1.substring(2,4));
            if(h1==0) z1="0"; if(m1==0) z2="0";
            h2 = Integer.parseInt(roomtime2.substring(0,2));
            m2 = Integer.parseInt(roomtime2.substring(2,4));
            if(h2==0) z3="0"; if(m2==0) z4="0";
            Roomauth.setText("인증 시간         "+z1+h1+"시 "+z2+m1+"분 ~ "+z3+h2+"시 "+z4+m2+"분");
        }
        Roomhow.setText("인증 방식         "+roomHowText);

        String days = ""; String day[] = {"월", "화", "수", "목", "금", "토", "일"};
        if(roomday){
            for(int i =0;i<roomwhen.length();i++){
                if(roomwhen.charAt(i)=='1'){
                    days += day[i]+ " ";
                }
            }
            Roomday.setText("인증 요일        "+days);
        }

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
                                DatabaseReference mDatabaseRef = database.getReference("study_rooms/" +name +"/member"); // 해당 스터디룸 찾아 들어가기

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저 정보 가져오기
                                String uid = user != null ? user.getUid() : null; // 로그인한 유저의 고유 uid 가져오기
                                //DatabaseReference mDatabaseRef = database.getReference("users/" + uid);

                                mDatabaseRef.child("member").child("name").setValue(uid); // 사용자 uid 삽입
                                curperson += 1;
                                mDatabaseRef.child("roomcurperson").setValue(curperson);
                                mDatabaseRef.child("roomnegcurperson").setValue(-1*curperson);


                                Map<String, Object> updates = new HashMap<String,Object>();
                                updates.put(uid, "true");
                                mDatabaseRef.updateChildren(updates);
                                finish();

                                //HashMap<String, Object> params = new HashMap<>();
                                //params.put("name", uid);
                                //mDatabaseRef.child("member").updateChildren(params); // 사용자 uid 삽입
                                //mDatabaseRef.child("member").push().setValue(uid);

                                //Map<String, Object> memberUpdates = new HashMap<>();
                                //memberUpdates.put("/member/name", uid);
                                //mDatabaseRef.updateChildren(memberUpdates);
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

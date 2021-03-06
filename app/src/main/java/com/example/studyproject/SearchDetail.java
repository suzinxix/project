package com.example.studyproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
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

    String roomcategory; long roomhoney; long curperson; // ????????????,  ???, ????????????
    int roomauth, roomhow; String roomtime1, roomtime2; // ?????? (??????/??????)
    Boolean roomday; String roomwhen; // ?????? ??????

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // ???????????? ?????? ?????? ????????????
    String uid = user != null ? user.getUid() : null; // ???????????? ????????? ?????? uid ????????????

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
        DatabaseReference joinRef = FirebaseDatabase.getInstance().getReference().child("study_rooms").child(name).child("member");

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
        Roomdate.setText("?????? ??????         " + date);
        Roomperson.setText("??????                  " + curperson +"/" +person + "???");
        Roompic.setImageResource(R.drawable.book);

        Roomcate.setText("????????????          "+roomcategory);
        String roomHowText=""; int h1, m1, h2, m2;
        if(roomhow==0){
            roomHowText="??????";
            Roomauth.setText("?????? ??????         ????????????  "+roomauth+"???");
        }
        else if(roomhow==1) {
            roomHowText = "??????";
            h1 = Integer.parseInt(roomtime1.substring(0,2));
            m1 = Integer.parseInt(roomtime1.substring(2,4));
            if(h1==0) z1="0"; if(m1==0) z2="0";
            h2 = Integer.parseInt(roomtime2.substring(0,2));
            m2 = Integer.parseInt(roomtime2.substring(2,4));
            if(h2==0) z3="0"; if(m2==0) z4="0";
            Roomauth.setText("?????? ??????         "+z1+h1+"??? "+z2+m1+"??? ~ "+z3+h2+"??? "+z4+m2+"???");
        }
        Roomhow.setText("?????? ??????         "+roomHowText);

        String days = ""; String day[] = {"???", "???", "???", "???", "???", "???", "???"};
        if(roomday){
            for(int i =0;i<roomwhen.length();i++){
                if(roomwhen.charAt(i)=='1'){
                    days += day[i]+ " ";
                }
            }
            Roomday.setText("?????? ??????        "+days);
        }

        Roompic.setImageResource(R.drawable.book); // default
        switch(roomcategory){
            case "??????":
                Roompic.setImageResource(R.drawable.habbit);
            case "??????":
                Roompic.setImageResource(R.drawable.book);
            case "??????":
                Roompic.setImageResource(R.drawable.hobby);
            case "??????":
                Roompic.setImageResource(R.drawable.exercise);
            case "??????":
                Roompic.setImageResource(R.drawable.book); // ??? ?????? ????????? ??? ?????????
        }


        //mDatabase = FirebaseDatabase.getInstance().getReference();
        bt = findViewById(R.id.bt_apply);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ???????????? ????????? ?????????
                AlertDialog.Builder dlg = new AlertDialog.Builder(SearchDetail.this);
                dlg.setTitle("Message") //??????
                        .setMessage("???????????? ??????????????? ?????????????????????????")
                        .setPositiveButton("???", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                joinRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        // ?????? ?????? ??????
                                        if (!snapshot.child(uid).exists()) {
                                            // ????????????????????? ????????? ????????? ?????? ?????? ??????
                                            FirebaseDatabase  database = FirebaseDatabase.getInstance();
                                            DatabaseReference mDatabaseRef = database.getReference("study_rooms/" +name +"/member"); // ?????? ???????????? ?????? ????????????
                                            DatabaseReference personRef = database.getReference("study_rooms/" + name);

                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // ???????????? ?????? ?????? ????????????
                                            String uid = user != null ? user.getUid() : null; // ???????????? ????????? ?????? uid ????????????

                                            Map<String, Object> updates = new HashMap<String,Object>();
                                            updates.put(uid, "true");
                                            mDatabaseRef.updateChildren(updates);
                                            // ????????? ????????? ??????
                                            long now = System.currentTimeMillis();
                                            Date joinDate = new Date(now);
                                            mDatabaseRef.child(uid + "/joined").setValue("true");
                                            mDatabaseRef.child(uid + "/joinDate").setValue(joinDate);

                                            // ??? ????????? ????????????????????? ??????
                                            mDatabaseRef.child(uid + "/joinDate/year").setValue(ServerValue.increment(1900));
                                            mDatabaseRef.child(uid + "/joinDate/month").setValue(ServerValue.increment(1));

                                            personRef.child("roomcurperson").setValue(ServerValue.increment(1));
                                            personRef.child("roomnegcurperson").setValue(ServerValue.increment(-1));

                                            Toast.makeText(SearchDetail.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(SearchDetail.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
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

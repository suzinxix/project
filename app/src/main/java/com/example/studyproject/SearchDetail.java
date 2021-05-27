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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Dictionary;


public class SearchDetail extends AppCompatActivity {
    //private ArrayList<UserStudyRoomDB> dataholder;
    //private MyRoomFragmentAdapter myadapter;
    //RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;
    //MakeRoomDB model;
    //private UserStudyRoomViewModel userStudyRoomViewModel;
    //public static final String EXTRA_STUDYROOMTITLE = "com.example.studyproject.EXTRA_STUDYROOMTITLE";
    //public static final String EXTRA_STUDYROOMGGUL = "com.example.studyproject.EXTRA_STUDYROOMGGUL";
    //public static final String EXTRA_STUDYROOMSTARTDAY = "com.example.studyproject.EXTRA_STUDYROOMSTARTDAY";

    TextView Roomname, Roominfo, Roomperson;
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
        Roomperson=findViewById(R.id.study_member);
        Roompic=findViewById(R.id.study_picture);

        String name = getIntent().getStringExtra("Roomname");
        String info = getIntent().getStringExtra("Roominfo");
        String person = getIntent().getStringExtra("Roomperson");

        Roomname.setText(name);
        Roominfo.setText(info);
        Roomperson.setText(person);
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
                                //saveNewStudyRoom(name, 0, 0);
                                //startActivityForResult(new Intent(SearchDetail.this, HomeActivity.class), 100);
                                //Intent data = new Intent();
                                //data.putExtra(EXTRA_STUDYROOMTITLE, name);
                                //data.putExtra(EXTRA_STUDYROOMGGUL, 0);
                                //data.putExtra(EXTRA_STUDYROOMSTARTDAY, 1);

                                //setResult(RESULT_OK, data);
                                Toast.makeText(SearchDetail.this,"스터디룸 가입이 완료되었습니다.",Toast.LENGTH_LONG).show();
                                finish();

                                //최종이길..
                                //자바 코드에서 프래그먼트 추가하는 방법
                                //MyRoomFragment mf = (MyRoomFragment) getSupportFragmentManager().findFragmentById(R.id.frag_myroom);
                                //mf.recyclerView = findViewById(R.id.myroom);

                                //mf.recyclerView.setAdapter(new MyRoomFragmentAdapter(mf.dataholder));
                                //mf.dataholder.add(new UserStudyRoomDB("쫌 돼라!!", 0, 0));
                                //mf.recyclerView.setAdapter(new MyRoomFragmentAdapter(mf.dataholder));

                                //new MyRoomFragmentAdapter(mf.dataholder).notifyDataSetChanged();

                                //((MyRoomFragment) getSupportFragmentManager().findFragmentByTag("fragmentTag")).testFunction();
                                //dataholder.add(new UserStudyRoomDB("쫌 돼라!!", 0, 0));
                                //recyclerView.setAdapter(new MyRoomFragmentAdapter(dataholder));
                                //recyclerView = findViewById(R.id.myroom);
                                //myadapter = new MyRoomFragmentAdapter(dataholder);
                                //MyRoomFragment myRoomFragment = new MyRoomFragment();
                                //Bundle bundle = new Bundle(1); // 전달하려는 값의 갯수
                                //bundle.putString("roomname", name);
                                //myRoomFragment.setArguments(bundle);
                                //getSupportFragmentManager().beginTransaction().replace(R.id.frag_myroom, new MyRoomFragment()).commit();
                                //myadapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = dlg.create();
                dlg.show();

                //ApplyDialog applyDialog = new ApplyDialog();
                //applyDialog.show(getSupportFragmentManager(), "example dialog");
            }
        });
    }
    /*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_detail, container, false);

        TextView study_title = view.findViewById(R.id.study_title);
        TextView study_info = view.findViewById(R.id.study_info);

        study_title.setText(Roomname);
        study_info.setText(Roominfo);

        return view;
    }
*/
/*
    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new SearchAllFragment());
    }*/
}


package com.example.studyproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SearchDetail extends AppCompatActivity {
    MakeRoomDB model;
    TextView Roomname, Roominfo, Roomperson;
    ImageView Roompic;
    public SearchDetail(){
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        Roomname=findViewById(R.id.study_title);
        Roominfo=findViewById(R.id.study_info);
        Roomperson=findViewById(R.id.study_member);
        Roompic=findViewById(R.id.study_picture);
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

        String name = getIntent().getStringExtra("Roomname");
        String info = getIntent().getStringExtra("Roominfo");
        String person = getIntent().getStringExtra("Roomperson");

        Roomname.setText(name);
        Roominfo.setText(info);
        Roomperson.setText(person);
        Roompic.setImageResource(R.drawable.book);
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


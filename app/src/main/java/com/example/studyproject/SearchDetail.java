package com.example.studyproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SearchDetail extends AppCompatActivity {
    TextView Roomname, Roominfo;
    public SearchDetail(){
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_detail);
        Roomname=findViewById(R.id.study_title);
        Roominfo=findViewById(R.id.study_info);

        String name = getIntent().getStringExtra("Roomname");
        String info = getIntent().getStringExtra("Roominfo");

        Roomname.setText(name);
        Roominfo.setText(info);
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


package com.example.studyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class WeeklyFragment extends Fragment implements View.OnClickListener {


    public WeeklyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_weekly, container, false);
        ImageButton ibt_camera = (ImageButton)view.findViewById(R.id.imageButtonCamera);
        ibt_camera.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}

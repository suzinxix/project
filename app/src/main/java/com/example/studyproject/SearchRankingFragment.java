package com.example.studyproject;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class SearchRankingFragment extends Fragment {

    TabLayout tabs; EditText etSearch; String searchKey; Boolean sch;

    SearchAllFragment allFragment;
    SearchHabitFragment habitFragment;
    SearchStudyFragment studyFragment;
    SearchHobbyFragment hobbyFragment;
    SearchExerciseFragment exerciseFragment;
    SearchEtcFragment etcFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search_ranking, container, false);
        FragmentTransaction ft=getFragmentManager().beginTransaction();

        allFragment = new SearchAllFragment();
        habitFragment = new SearchHabitFragment();
        studyFragment = new SearchStudyFragment();
        hobbyFragment = new SearchHobbyFragment();
        exerciseFragment = new SearchExerciseFragment();
        etcFragment = new SearchEtcFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.container, allFragment).commit();

        tabs = rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("전체"));
        tabs.addTab(tabs.newTab().setText("습관"));
        tabs.addTab(tabs.newTab().setText("공부"));
        tabs.addTab(tabs.newTab().setText("취미"));
        tabs.addTab(tabs.newTab().setText("운동"));
        tabs.addTab(tabs.newTab().setText("기타"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0)
                    selected = allFragment;
                else if(position == 1)
                    selected = habitFragment;
                else if(position == 2)
                    selected = studyFragment;
                else if(position == 3)
                    selected = hobbyFragment;
                else if(position == 4)
                    selected = exerciseFragment;
                else if(position == 5)
                    selected = etcFragment;

                if(!selected.isAdded())
                {
                    getChildFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return rootView;
    }

}

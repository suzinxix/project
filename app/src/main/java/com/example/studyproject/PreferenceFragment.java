package com.example.studyproject;

import android.os.Bundle;

public class PreferenceFragment extends android.preference.PreferenceFragment {


    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);
    }
}

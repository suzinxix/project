package com.example.studyproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import static com.example.studyproject.SearchDetail.EXTRA_STUDYROOMTITLE;

public class HomeActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    private UserStudyRoomViewModel userStudyRoomViewModel;
    HomeFragment fragment_home;
    SearchFragment fragment_studysearch;
    PersonalPageFragment fragment_personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragment_home = new HomeFragment();
        fragment_studysearch = new SearchFragment();
        fragment_personal = new PersonalPageFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, HomeFragment.newInstance()).commit();

        //Intent intent = new Intent(HomeActivity.this, SearchDetail.class);
        //startActivityForResult(intent, 1);

        // https://www.youtube.com/watch?v=M5RuFo-85cY
        // Firebase cloud message 받아오는 부분
        FirebaseMessaging.getInstance().subscribeToTopic("alert")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if(!task.isSuccessful()){
                            msg = "Failed";
                        }
                    }
                });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment_home).commit();

                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment_studysearch).commit();

                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment_personal).commit();
                        return true;
                }

                return false;
            }
        });
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(EXTRA_STUDYROOMTITLE);
            int ggul = data.getIntExtra(SearchDetail.EXTRA_STUDYROOMGGUL, 0);
            int startday = data.getIntExtra(SearchDetail.EXTRA_STUDYROOMSTARTDAY, 1);

            UserStudyRoom userStudyRoom = new UserStudyRoom(title, ggul, startday);
            userStudyRoomViewModel.insert(userStudyRoom);

            Toast.makeText(this, "저장을 완료하였습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "저장을 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }
}
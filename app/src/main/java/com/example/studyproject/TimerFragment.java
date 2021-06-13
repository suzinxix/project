package com.example.studyproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TimerFragment extends Fragment implements View.OnClickListener {
    TextView tv_time;
    Button ibt_start;
    Button bt_quit;
    WeeklyFragment fragment_weekly;
    private DatabaseReference timerRef;
    final static int Init = 0;
    final static int Run = 1;
    final static int Pause = 2;
    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    long myBaseTime, myPauseTime, timer_s;
    String room_name, weekname, timer;
    String[] timer_num;

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        tv_time = (TextView)view.findViewById(R.id.textViewTime);
        ibt_start = (Button) view.findViewById(R.id.imageButtonStart);
        ibt_start.setOnClickListener(this);
        fragment_weekly = new WeeklyFragment();

        Bundle bundle = getArguments();
        if(bundle != null) {
            room_name= bundle.getString("key_roomname");
            weekname =bundle.getString("weekname");
        }

        timerRef = FirebaseDatabase.getInstance().getReference().child("study_rooms").child(room_name).child("roomtodo").child(weekname).child("timer");

        bt_quit =  (Button)view.findViewById(R.id.buttonQuit);
        bt_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerRef.setValue(getTimeOut());

                Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                bundle.putString("key_roomname", room_name);//번들에 넘길 값 저장
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                WeeklyFragment weeklyFragment = new WeeklyFragment();//프래그먼트2 선언
                weeklyFragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                transaction.replace(R.id.containerTabs, weeklyFragment);
                transaction.commit();
            }
        });

        if (timerRef != null) {
            timerRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() != null) {
                        timer = snapshot.getValue().toString();
                        timer_num = timer.split(":");
                        timer_s = Integer.parseInt(timer_num[0])*3600 + Integer.parseInt(timer_num[1])*60 + Integer.parseInt(timer_num[2]);
                        tv_time.setText(timer);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (cur_Status) {
            case Init:
                myBaseTime = SystemClock.elapsedRealtime();
                System.out.println(myBaseTime);
                //myTimer이라는 핸들러를 빈 메세지를 보내서 호출
                myTimer.sendEmptyMessage(0);
                ibt_start.setText("멈춤");
                cur_Status = Run; //현재상태를 런상태로 변경
                break;
            case Run:
                myTimer.removeMessages(0); //핸들러 메세지 제거
                myPauseTime = SystemClock.elapsedRealtime();
                ibt_start.setText("계속");
                cur_Status = Pause;
                break;
            case Pause:
                long now = SystemClock.elapsedRealtime();
                myTimer.sendEmptyMessage(0);
                myBaseTime += (now- myPauseTime);
                cur_Status = Run;
                ibt_start.setText("멈춤");
                break;
        }
    }

    Handler myTimer = new Handler() {
        public void handleMessage(Message msg){
            tv_time.setText(getTimeOut());

            //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
            myTimer.sendEmptyMessage(0);
        }
    };

    //현재시간을 계속 구해서 출력하는 메소드
    String getTimeOut(){
        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간
        long outTime = timer_s*1000 + now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d", outTime/ 1000/ 3600, outTime/1000 / 60 %60, (outTime/1000)%60);
        return easy_outTime;
    }
}

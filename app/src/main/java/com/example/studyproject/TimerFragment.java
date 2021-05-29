package com.example.studyproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment implements View.OnClickListener {
    TextView tv_time;
    ImageButton ibt_start;
    Button bt_quit;
    WeeklyFragment fragment_weekly;

    final static int Init = 0;
    final static int Run = 1;
    final static int Pause = 2;
    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    long myBaseTime;
    long myPauseTime;

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        tv_time = (TextView)view.findViewById(R.id.textViewTime);

        ibt_start = (ImageButton) view.findViewById(R.id.imageButtonStart);
        ibt_start.setOnClickListener(this);

        fragment_weekly = new WeeklyFragment();
        bt_quit =  (Button)view.findViewById(R.id.buttonQuit);
        bt_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerTabs, fragment_weekly).commit();
            }
        });

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
//                      ibt_start 이미지 변경 '멈춤'
                cur_Status = Run; //현재상태를 런상태로 변경
                break;
            case Run:
                myTimer.removeMessages(0); //핸들러 메세지 제거
                myPauseTime = SystemClock.elapsedRealtime();
//                      이미지 변경
                cur_Status = Pause;
                break;
            case Pause:
                long now = SystemClock.elapsedRealtime();
                myTimer.sendEmptyMessage(0);
                myBaseTime += (now- myPauseTime);
//                      이미지 변경
                cur_Status = Run;
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
        long outTime = now - myBaseTime;
        String easy_outTime = String.format("%02d:%02d:%02d", outTime/ 1000/ 3600, outTime/1000 / 60 %60, (outTime/1000)%60);
        return easy_outTime;

    }
}

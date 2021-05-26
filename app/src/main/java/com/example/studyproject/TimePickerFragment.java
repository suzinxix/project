package com.example.studyproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.studyproject.MakeRoom.roomTimeSt;
import static com.example.studyproject.MakeRoom.saveSH;
import static com.example.studyproject.MakeRoom.saveSM;
import static com.example.studyproject.MakeRoom.saveFH;
import static com.example.studyproject.MakeRoom.saveFM;
import static com.example.studyproject.MakeRoom.chkS;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour, min;

        if(chkS){
            hour = saveSH; min = saveSM;
        } else{
            hour = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(), this, hour, min, DateFormat.is24HourFormat(getContext())
        );

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String amPm="오전"; String addi="";

        if((hourOfDay*60+minute)<(saveSH*60+saveSM)){
            // 끝나는 시간이 시작 시간보다 빠르지 않도록
            hourOfDay=saveSH; minute=saveSM;
            Toast.makeText(getContext(),"시작 시각보다 종료 시각을 빠르게 설정할 수 없습니다.", Toast.LENGTH_LONG).show();
        }

        if(hourOfDay>11) amPm="오후";
        int curHour;
        if(hourOfDay>11){
            curHour=hourOfDay-12;
        } else curHour=hourOfDay;
        if(minute<10) addi="0";


        Button st = (Button)getActivity().findViewById(R.id.bt_stTime);
        st.setTag(String.valueOf(hourOfDay)+String.valueOf(minute));
        st.setText(amPm+" "+String.valueOf(curHour)+":"+addi+String.valueOf(minute));
        // MakeRoom 정보 전달
        roomTimeSt = String.valueOf(hourOfDay)+String.valueOf(minute);
        saveSH = hourOfDay; saveSM = minute; chkS=true;
    }
}
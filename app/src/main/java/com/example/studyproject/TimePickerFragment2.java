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

import java.util.Calendar;

import static com.example.studyproject.MakeRoom.roomTimeFn;
import static com.example.studyproject.MakeRoom.roomTimeSt;


public class TimePickerFragment2 extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    boolean chk=false;
    int saveH, saveM;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour, min;

        if(chk){
            hour = saveH; min = saveM;
        } else{
            hour = c.get(Calendar.HOUR_OF_DAY);
            min = c.get(Calendar.MINUTE);
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(), this, hour, min, DateFormat.is24HourFormat(getContext())
        ); // getActivity??

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String amPm="오전";
        if(hourOfDay>11) amPm="오후";
        int curHour;
        if(hourOfDay>11){
            curHour=hourOfDay-12;
        } else curHour=hourOfDay;
        Button ed = (Button)getActivity().findViewById(R.id.bt_edTime);
        ed.setTag(String.valueOf(hourOfDay)+String.valueOf(minute));
        ed.setText(amPm+" "+String.valueOf(curHour)+":"+String.valueOf(minute));
        // MakeRoom 정보 전달
        roomTimeSt = String.valueOf(hourOfDay)+String.valueOf(minute);
        saveH = hourOfDay; saveM = minute; chk=true;
    }
}
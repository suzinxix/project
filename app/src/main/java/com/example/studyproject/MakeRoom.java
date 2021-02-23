package com.example.studyproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@IgnoreExtraProperties
public class MakeRoom extends AppCompatActivity{
    Button bt_makeroom;
    EditText et_roomname;
    EditText et_roomcategory;
    EditText et_roominfo;
    EditText et_roomauth;
    CheckBox[] cb=new CheckBox[7]; Switch sw_day; boolean day = false;
    Button bt_stTime, bt_edTime;

    private ListView listView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    List roomList = new ArrayList<>();
    private ArrayAdapter<String> dataAdapter;

    String roomname;
    String roomcategory;
    String roominfo;
    String roomauth;
    String sort = "roomcategory";
    int[] roomDay = {0,0,0,0,0,0,0};
    String roomTimeSt="0800";
    String roomTimeFn="2200";



    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeroom);

        bt_makeroom = (Button) findViewById(R.id.bt_makeroom);
        et_roomname = (EditText) findViewById(R.id.et_roomname);
        et_roomcategory = (EditText) findViewById(R.id.et_roomcategory);
        et_roominfo = (EditText) findViewById(R.id.et_roominfo);
        et_roomauth = (EditText) findViewById(R.id.et_roomauth);
        bt_stTime = (Button)findViewById(R.id.bt_stTime);
        bt_edTime = (Button)findViewById(R.id.bt_edTime);

        // 인증 요일
        sw_day = (Switch)findViewById(R.id.switch1);
        cb[0] = (CheckBox)findViewById(R.id.bt_mon); cb[1] = (CheckBox)findViewById(R.id.bt_tus);
        cb[2] = (CheckBox)findViewById(R.id.bt_wed); cb[3] = (CheckBox)findViewById(R.id.bt_thu);
        cb[4] = (CheckBox)findViewById(R.id.bt_fri); cb[5] = (CheckBox)findViewById(R.id.bt_sat);
        cb[6] = (CheckBox)findViewById(R.id.bt_sun);
        for(int i =0;i<cb.length;i++) cb[i].setEnabled(false); // 체크박스 비활성화

        mDatabase = FirebaseDatabase.getInstance().getReference();

        sw_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    for (int i = 0; i < cb.length; i++) cb[i].setEnabled(true); // 체크박스 활성화
                    day = true;
                }else {
                    for (int i = 0; i < cb.length; i++) cb[i].setEnabled(false); // 체크박스 비활성화
                    day = false;
                }
            }
        });
        for(int i =0;i<cb.length;i++) {
            final int k = i;
            cb[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) roomDay[k]=1;
                    else roomDay[k]=0;
                }
            });
        }

    // timepicker 설정하기
        bt_stTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        bt_edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment2 timePickerFragment2 = new TimePickerFragment2();
                timePickerFragment2.show(getSupportFragmentManager(), "timePicker2");
            }
        });

        bt_makeroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getRoomname = et_roomname.getText().toString();
                String getRoomcategory = et_roomcategory.getText().toString();
                String getRoominfo = et_roominfo.getText().toString();
                String getRoomauth = et_roomauth.getText().toString();
                boolean getDay = day; final int[] getRoomDay = roomDay; // 인증 요일 사용 여부 / 인증요일
                // Timepicker로 정보 받아오기

                writeNewRoom(getRoomname, getRoomcategory, getRoominfo, getRoomauth);
                readRoomDB();
            }
        });
    }

    private void writeNewRoom(String roomname, String roomcategory, String roominfo, String roomauth) {
        //String key = mDatabase.child("rooms").push().getKey();
        MakeRoomDB roomDB = new MakeRoomDB(roomname, roomcategory, roominfo, roomauth);
        Map<String, Object> roomValues = roomDB.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/study_rooms/" + roomname, roomValues);

        mDatabase.updateChildren(childUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MakeRoom.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MakeRoom.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void readRoomDB(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot.getValue(MakeRoomDB.class) != null){
                    MakeRoomDB post = dataSnapshot.getValue(MakeRoomDB.class);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    Toast.makeText(MakeRoom.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }



    /*

    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeroom);

        bt_makeroom = (Button) findViewById(R.id.bt_makeroom);
        et_roomname = (EditText) findViewById(R.id.et_roomname);
        et_roomcategory = (EditText) findViewById(R.id.et_roomcategory);
        et_roominfo = (EditText) findViewById(R.id.et_roominfo);
        et_roomauth = (EditText) findViewById(R.id.et_roomauth);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.db_list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(longClickListener);

        getFirebaseDatabase();
        bt_makeroom.setEnabled(true);
    }

    public void setInsertMode(){
        et_roomname.setText("");
        et_roomcategory.setText("");
        et_roominfo.setText("");
        et_roominfo.setText("");
        bt_makeroom.setEnabled(true);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "Position = " + position);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            et_roomname.setText(tempData[0].trim());
            et_roomcategory.setText(tempData[1].trim());
            et_roominfo.setText(tempData[2].trim());
            et_roomauth.setText(tempData[3].trim());

            et_roomname.setEnabled(false);
            bt_makeroom.setEnabled(false);
        }
    };

    // 길게 누르면 데이터 삭제하는 기능
    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long click", "position = " + position);
            final String[] nowData = arrayData.get(position).split("\\s");
            roomname = nowData[0];
            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2] + ", " + nowData[3] + ", ";
            AlertDialog.Builder dialog = new AlertDialog.Builder(MakeRoom.this);
            dialog.setTitle("스터디룸 삭제")
                    .setMessage("스터디룸을 삭제하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            postFirebaseDatabase(roomname, roomcategory, roomauth, roominfo);
                            getFirebaseDatabase();
                            setInsertMode();
                            et_roomname.setEnabled(true);
                            Toast.makeText(MakeRoom.this, "스터디룸을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MakeRoom.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                            setInsertMode();
                            et_roomname.setEnabled(true);
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };

    // 중복 체크
    public boolean IsExistName(){
        boolean IsExist = arrayIndex.contains(roomname);
        return IsExist;
    }

    public void postFirebaseDatabase(String roomname, String roomcategory, String roominfo, String roomauth){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
//        if(add){
//            MakeRoomDB post = new MakeRoomDB(roomname, roomcategory, roominfo, roomauth);
//            postValues = post.toMap();
//        }
        MakeRoomDB post = new MakeRoomDB(roomname, roomcategory, roominfo, roomauth);
        postValues = post.toMap();

        childUpdates.put("/study_room/" + roomname, postValues);
        mPostReference.updateChildren(childUpdates); // 다른 하위 노드를 덮어쓰지 않고 특정 하위 노드에 동시에 쓰는 메서드
    }

    public void getFirebaseDatabase(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("getFireDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    MakeRoomDB get = postSnapshot.getValue(MakeRoomDB.class);
                    String[] info = {get.roomname, get.roomcategory, get.roominfo, get.roomauth};
                    String Result = setTextLength(info[0], 10) + setTextLength(info[1], 10)+ setTextLength(info[2], 10)+ setTextLength(info[3], 10);
                    arrayData.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase" , "key: " + key);
                    Log.d("getFirebaseDatabase", "info : " + info[0] + info[1] + info[2] + info[3]);
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayData);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        Query sortbyCategory = FirebaseDatabase.getInstance().getReference().child("study_room").orderByChild(sort);
        sortbyCategory.addListenerForSingleValueEvent(postListener);
    }

    public String setTextLength(String text, int length) {
        if(text.length()< length){
            int gap = length - text.length();
            for(int i=0; i<gap;i++){
                text = text + " ";
            }
        }
        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_makeroom:
                roomname = et_roomname.getText().toString();
                roomcategory = et_roomcategory.getText().toString();
                roominfo = et_roominfo.getText().toString();
                roomauth = et_roomauth.getText().toString();
                // 중복 체크 검사하는 기능인데 작동을 제대로 안해서 주석처리 했습니다ㅠ
//                if(!IsExistName()){
//                    postFirebaseDatabase(roomname, roomcategory, roominfo, roomauth);
//                    getFirebaseDatabase();
//                    setInsertMode();
//                    et_roomname.setEnabled(true);
//                }
//                else{
//                    Toast.makeText(MakeRoom.this, "이미 존재하는 스터디룸 이름입니다. 다른 이름으로 변경해주세요.", Toast.LENGTH_LONG).show();
//                }

                postFirebaseDatabase(roomname, roomcategory, roominfo, roomauth);
                getFirebaseDatabase();
                setInsertMode();
                et_roomname.requestFocus();
                et_roomname.setCursorVisible(true);
                break;
        }
    }*/
}

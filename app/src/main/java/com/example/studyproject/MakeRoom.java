package com.example.studyproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeRoom extends AppCompatActivity implements View.OnClickListener{
    private DatabaseReference mPostReference;
    Button bt_makeroom;
    EditText et_roomname;
    EditText et_roomcategory;
    EditText et_roominfo;
    EditText et_roomauth;

    String roomname;
    String roomcategory;
    String roominfo;
    String roomauth;
    String sort = "roomcategory";

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
                            postFirebaseDatabase(false);
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

    public void postFirebaseDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            MakeRoomDB post = new MakeRoomDB(roomname, roomcategory, roominfo, roomauth);
            postValues = post.toMap();
        }
        childUpdates.put("/study_room/" + roomname, postValues);
        mPostReference.updateChildren(childUpdates);
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
                if(!IsExistName()){
                    postFirebaseDatabase(true);
                    getFirebaseDatabase();
                    setInsertMode();
                    et_roomname.setEnabled(true);
                }
                else{
                    Toast.makeText(MakeRoom.this, "이미 존재하는 스터디룸 이름입니다. 다른 이름으로 변경해주세요.", Toast.LENGTH_LONG).show();
                }
                et_roomname.requestFocus();
                et_roomname.setCursorVisible(true);
                break;
        }
    }
}

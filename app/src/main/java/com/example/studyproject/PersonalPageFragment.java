package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalPageFragment extends Fragment {
    private DatabaseReference mDatabase;
    Button bt_personal;
    Button bt_profileEdit;
    EditText personalName, personalNick;
    TextView tv_pname, tv_pnick;
    String myname;
    String nickname;
    Toolbar toolbar_personal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        setHasOptionsMenu(true); // 메뉴가 있음을 알림
        //personalName = (EditText)view.findViewById(R.id.et_pname);
        //personalNick = (EditText) view.findViewById(R.id.et_pnick);
        //bt_personal = (Button) view.findViewById(R.id.bt_save);
        tv_pname = (TextView) view.findViewById(R.id.tv_pname);
        tv_pnick = (TextView) view.findViewById(R.id.tv_pnick);
        //bt_profileEdit = view.findViewById(R.id.profileEdit);

        // 툴바 추가
        toolbar_personal = (Toolbar) view.findViewById(R.id.toolbarPersonal);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_personal);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.getValue(User.class) != null) {
                    User post = dataSnapshot.getValue(User.class);
                    nickname = post.getNickname();
                    tv_pnick.setText(nickname);
                    Log.w("FireBaseData", "getData" + post.toString());
                } else {
                    //Toast.makeText(MainActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("FireBaseData", "loadPost:onCancelled", databaseError.toException());
            }
        });




    /*
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User user1 = dataSnapshot.getValue(User.class);
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.addValueEventListener(postListener);*/


        if (user != null) {
            myname = user.getDisplayName();

            // Check if user's email is verified
        }

        tv_pname.setText("이름: " + myname);


        /*
        bt_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalNick.setText(personalNick.getText());
            }
        });

         */
        /*
        bt_profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ProfileEditActivity.class));
            }
        });



         */
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_personal, menu) ;
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                //((HomeActivity)getActivity()).replaceFragment(SettingFragment); //Fragment 변경


                break;
            case R.id.studylist:
                // d
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}

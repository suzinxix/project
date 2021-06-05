package com.example.studyproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    Button button2;
    EditText etSearch;
    static String searchKey=""; static Boolean sch=false;
    FragmentTransaction ft2;

    public SearchFragment(){

    }

    //static boolean calledAlready = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Fragment searchRankingFragment = new SearchRankingFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.container, searchRankingFragment).addToBackStack(null).commit();

        etSearch = (EditText)view.findViewById(R.id.editSearch);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(etSearch.getText().toString().length() != 0) {

                        searchKey = etSearch.getText().toString();
                        etSearch.setText(searchKey);
                        sch=true;

                        // fragment reload
                       ft2 = getChildFragmentManager().beginTransaction();
                        if (Build.VERSION.SDK_INT >= 26) {
                            ft2.setReorderingAllowed(false);
                        }
                        ft2.detach(searchRankingFragment).attach(searchRankingFragment).commit();


                    }else {
                        searchKey = "";
                        sch=false;

                        // fragment reload
                        ft2 = getChildFragmentManager().beginTransaction();
                        if (Build.VERSION.SDK_INT >= 26) {
                            ft2.setReorderingAllowed(false);
                        }
                        ft2.detach(searchRankingFragment).attach(searchRankingFragment).commit();

                    }
                    return true;
                }
                return false;
            }
        });

       button2 = (Button)view.findViewById(R.id.button2);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // fragment 에서 Activity 이동해야함

                getActivity().startActivity(new Intent(getActivity(), MakeRoom.class));
            }
        });



        /*listView = (ListView)findViewById(R.id.room_list);
        * //DB 변수 초기화
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("study_rooms");

                dataAdapter = new ArrayAdapter<String>(MakeRoom.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>()); //아오!

                listView.setAdapter(dataAdapter);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataAdapter.clear();
                        for(DataSnapshot messageData : snapshot.getChildren()){
                            String msg = messageData.getValue().toString();
                            dataAdapter.add(msg);
                        }
                        dataAdapter.notifyDataSetChanged();
                        listView.setSelection(dataAdapter.getCount() - 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        *
        * */


        // db 리스트뷰 띄우는 건데 SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length 에러 발생해서 주석 처리함
/*
        if(!calledAlready){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }
        ListView listView = (ListView) view.findViewById(R.id.room_list);

        adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                roomList
        );
        /*
        listView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaserRef = database.getReference("roomdb");

        databaserRef.child("study_rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot fileSnapshot : snapshot.getChildren()) {
                    String str = fileSnapshot.child("roomname").getValue(String.class);
                    Log.i("TAG : value is ", str);
                    roomList.add(str);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG : ", "Failed to read value", error.toException());
            }
        });*/
        return view;
    }

}
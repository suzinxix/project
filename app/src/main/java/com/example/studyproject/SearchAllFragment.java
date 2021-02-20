package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchAllFragment extends Fragment {
    private RecyclerView recview;
    private View ContactsView;
    private DatabaseReference ContactsRef, RoomRef;
//    Searchadapter adapter;


    public SearchAllFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//    //public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search_all, container, false);
        ContactsView = inflater.inflate(R.layout.fragment_search_all, container, false);

//        //recview=(RecyclerView)rootView.findViewById(R.id.recview);
        recview=(RecyclerView)ContactsView.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        ContactsRef = FirebaseDatabase.getInstance().getReference().child("study_rooms");
        RoomRef = FirebaseDatabase.getInstance().getReference().child("study_rooms");

//        return rootView;
        return ContactsView;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MakeRoomDB> options =
                new FirebaseRecyclerOptions.Builder<MakeRoomDB>()
                        .setQuery(ContactsRef, MakeRoomDB.class)
                        //.setQuery(FirebaseDatabase.getInstance().getReference().child("study_rooms"), MakeRoomDB.class) // setQuery 부분은 노드 데이터 읽어오는 것
                        .build();

        FirebaseRecyclerAdapter<MakeRoomDB, ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<MakeRoomDB, ContactsViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, int position, @NonNull MakeRoomDB model) {
                String roomID = getRef(position).getKey();
                RoomRef.child(roomID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("study_rooms")){
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();

                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                        }
                        else{
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();

                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlerowdesign, viewGroup, false);
                ContactsViewHolder viewHolder = new ContactsViewHolder(view);
                return viewHolder;
            }
        };
//        adapter = new Searchadapter(options);
        recview.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView roomname, roominfo;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            roomname = itemView.findViewById(R.id.text_1);
            roominfo = itemView.findViewById(R.id.text_2);
        }
    }



}

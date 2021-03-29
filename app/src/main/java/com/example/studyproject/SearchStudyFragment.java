package com.example.studyproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SearchStudyFragment extends Fragment {
    private RecyclerView recview_5;
    private View ContactsView;
    private DatabaseReference ContactsRef, RoomRef;
    Query query = FirebaseDatabase.getInstance().getReference("study_rooms")
            .orderByChild("roomcategory")
            .equalTo("study");

    public SearchStudyFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactsView = inflater.inflate(R.layout.fragment_search_study, container, false);
        recview_5 = (RecyclerView) ContactsView.findViewById(R.id.recview_5);
        recview_5.setLayoutManager(new LinearLayoutManager(getContext()));

        ContactsRef = FirebaseDatabase.getInstance().getReference().child("study_rooms");
        RoomRef = FirebaseDatabase.getInstance().getReference().child("study_rooms");
        return ContactsView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<MakeRoomDB> options =
                new FirebaseRecyclerOptions.Builder<MakeRoomDB>()
                        .setQuery(query, MakeRoomDB.class) // 노드 데이터 읽어오기
                        .build();

        FirebaseRecyclerAdapter<MakeRoomDB, SearchStudyFragment.ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<MakeRoomDB, SearchStudyFragment.ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SearchStudyFragment.ContactsViewHolder holder, int position, @NonNull MakeRoomDB model) {
                String roomId = getRef(position).getKey(); // 리사이클러뷰
                final String name = model.getRoomname();
                final String info = model.getRoominfo();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SearchDetail.class);
                        intent.putExtra("Roomname", ""+name);
                        intent.putExtra("Roominfo", info);
                        startActivity(intent);
                    }
                });

                RoomRef.child(roomId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("study_rooms")) {
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();

                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                        } else {
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
            public SearchStudyFragment.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.singlerowdesign, viewGroup, false);
                SearchStudyFragment.ContactsViewHolder viewHolder = new SearchStudyFragment.ContactsViewHolder(view);
                return viewHolder;
            }
        };
        recview_5.setAdapter(adapter);
        adapter.startListening();
    }
    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView roomname, roominfo;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            roomname = itemView.findViewById(R.id.text_1);
            roominfo = itemView.findViewById(R.id.text_2);
        }
    }
}

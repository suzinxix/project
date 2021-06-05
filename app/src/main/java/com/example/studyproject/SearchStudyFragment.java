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

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchStudyFragment extends Fragment {
    private RecyclerView recview_5;
    private View ContactsView;
    private DatabaseReference ContactsRef, RoomRef;
    Query query = FirebaseDatabase.getInstance().getReference("study_rooms")
            .orderByChild("roomcategory")
            .equalTo("공부");

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

        FirebaseRecyclerAdapter<MakeRoomDB, SearchStudyFragment.ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<MakeRoomDB, SearchStudyFragment.ContactsViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull final SearchStudyFragment.ContactsViewHolder holder, int position, @NonNull final MakeRoomDB model) {
                final String roomID = getRef(position).getKey();
                final String name = model.getRoomname();
                final String info = model.getRoominfo();
                final Date date = model.getRoomdate();
                final String person = model.getRoomperson();

                // 현재 날짜 형식 변환
                SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String nowdate = simpleDate.format(date);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SearchDetail.class);
                        intent.putExtra("Roomname", ""+name);
                        intent.putExtra("Roominfo", info);
                        intent.putExtra("Roomdate", nowdate);
                        intent.putExtra("Roomperson", person);
                        startActivity(intent);
                    }
                });

                RoomRef.child(roomID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int room_index=1;
                        if(snapshot.hasChild("study_rooms")){
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();
                            String room_person = snapshot.child("roomperson").getValue().toString();

                            room_index++;
                            holder.roomindex.setText("" + room_index);
                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                            holder.roomperson.setText(room_person + "명");
                        }
                        else{
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();
                            String room_person = snapshot.child("roomperson").getValue().toString();
                            String room_cate = snapshot.child("roomcategory").getValue().toString();

                            holder.roomindex.setText("" + room_index);
                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                            holder.roomperson.setText(room_person + "명");
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


    // 홀더
    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView roomindex, roomname, roominfo, roomperson;

        //View v;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            roomindex = itemView.findViewById(R.id.text_index);
            roomname = itemView.findViewById(R.id.text_1);
            roominfo = itemView.findViewById(R.id.text_2);
            roomperson = itemView.findViewById(R.id.text_3);
        }
    }
}

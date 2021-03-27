package com.example.studyproject;

import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    public SearchAllFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactsView = inflater.inflate(R.layout.fragment_search_all, container, false);
        recview=(RecyclerView)ContactsView.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        ContactsRef = FirebaseDatabase.getInstance().getReference().child("study_rooms");
        RoomRef = FirebaseDatabase.getInstance().getReference().child("study_rooms");

        return ContactsView;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MakeRoomDB> options =
                new FirebaseRecyclerOptions.Builder<MakeRoomDB>()
                        .setQuery(ContactsRef, MakeRoomDB.class) // 노드 데이터 읽어오기
                        .build();

        FirebaseRecyclerAdapter<MakeRoomDB, ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<MakeRoomDB, ContactsViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, int position, @NonNull final MakeRoomDB model) {
                final String roomID = getRef(position).getKey();
                final String name = model.getRoomname();
                final String info = model.getRoominfo();
                final String person = model.getRoomperson();

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SearchDetail.class);
                        intent.putExtra("Roomname", ""+name);
                        intent.putExtra("Roominfo", info);
                        intent.putExtra("Roomperson", person);
                        startActivity(intent);
                    }
                });

                RoomRef.child(roomID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int room_index = 0;
                        if(snapshot.hasChild("study_rooms")){
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();
                            String room_person = snapshot.child("roomperson").getValue().toString();

                            room_index++;
                            holder.roomindex.setText("" + room_index);
                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                            holder.roomperson.setText("/" + room_person);
                        }
                        else{
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();
                            String room_person = snapshot.child("roomperson").getValue().toString();

                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                            holder.roomperson.setText(room_person);
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
        recview.setAdapter(adapter);
        adapter.startListening();
    }


    // 홀더
    public static class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView roomindex, roomname, roominfo, roomperson;
        //View v;
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            roomindex = itemView.findViewById(R.id.text_index);
            roomname = itemView.findViewById(R.id.text_1);
            roominfo = itemView.findViewById(R.id.text_2);
            roomperson = itemView.findViewById(R.id.text_3);

            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick();
//                        Intent intent = new Intent(SearchAllFragment, SearchDetail.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("TEXT", roomname.get(pos));
//                        AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new SearchDetail(model.getRoomname(), model.getRoominfo()));
                    }
                }
            });*/
        }
    }
    /*
    public interface OnItemClickListener {
        void onItemClick(DataSnapshot snapshot, int pos);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }*/

}

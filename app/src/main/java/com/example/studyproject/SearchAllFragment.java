package com.example.studyproject;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 검색 단어
import static com.example.studyproject.SearchFragment.searchKey;
import static com.example.studyproject.SearchFragment.sch;

public class SearchAllFragment extends Fragment {
    private RecyclerView recview; EditText etSearch;
    private View ContactsView;
    private DatabaseReference ContactsRef, RoomRef;
    SearchAllFragment allFragment;
    Button bt_sort;
    int cnt = 0; // 0: 이름순, 1: 회원수 순, 2: 꿀 순
    String sort1 = "roomname"; String bt_text = "이름 순";
    FragmentTransaction ft;

    Query query = FirebaseDatabase.getInstance().getReference("study_rooms")
            .orderByChild("roomname");
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

        bt_sort = (Button)ContactsView.findViewById(R.id.button_sort);

        bt_sort.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                cnt++; if(cnt>1) cnt=0;
                switch (cnt){
                    case 0:
                        sort1 = "roomname"; bt_text="이름 순"; break;
                    case 1:
                        sort1 = "roomneghoney"; bt_text="꿀 순"; break;
                }
                query = FirebaseDatabase.getInstance().getReference("study_rooms")
                        .orderByChild(sort1);
                bt_sort.setText(bt_text);
                onStart();
            }
        });


        if(sch&&!(searchKey.equals(""))) { // 검색 결과가 존재할 때
           query = FirebaseDatabase.getInstance().getReference("study_rooms")
                    .orderByChild("roomname")
                   .startAt(searchKey).endAt(searchKey+"\uf8ff");
        }



        return ContactsView;
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<MakeRoomDB> options =
                new FirebaseRecyclerOptions.Builder<MakeRoomDB>()
                        .setQuery(query, MakeRoomDB.class) // 노드 데이터 읽어오기
                        .build();



        FirebaseRecyclerAdapter<MakeRoomDB, ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<MakeRoomDB, ContactsViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, int position, @NonNull final MakeRoomDB model) {
                final String roomID = getRef(position).getKey();
                final String name = model.getRoomname();
                final String info = model.getRoominfo();
                final Date date = model.getRoomdate();
                final String person = model.getRoomperson();


                // 정보 받아오기 (2)
                final String roomcate = model.getRoomcategory();
                final long roomhoney = model.getRoomhoney();
                final long curperson = model.getRoomcurperson();
                final String roomauth = model.getRoomauth();
                final int roomhow = model.getRoomhow();
                final String roomt1 = model.getRoomtime1();
                final String roomt2 = model.getRoomtime2();
                final Boolean roomd = model.getRoomday();
                final List<Integer> roomwhen = model.getRoomwhen();
                String roomwhen1="";
                for(int i=0;i<roomwhen.size();i++){
                    roomwhen1 += String.valueOf(roomwhen.get(i));
                }
                final String roomwhen2 = roomwhen1;



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

                        // 정보 넘기기 (2)
                        intent.putExtra("Roomcategory", roomcate);
                        intent.putExtra("Roomhoney", roomhoney);
                        intent.putExtra("Roomcurperson", curperson);
                        intent.putExtra("Roomauth", roomauth);
                        intent.putExtra("Roomhow", roomhow);
                        intent.putExtra("Roomtime1", roomt1);
                        intent.putExtra("Roomtime2", roomt2);
                        intent.putExtra("Roomday", roomd);
                        intent.putExtra("Roomwhen", roomwhen2);

                        startActivity(intent);
                    }
                });

                RoomRef.child(roomID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int room_index=1;
                        if(snapshot.hasChild("study_rooms")){
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();
                            String room_person = snapshot.child("roomperson").getValue().toString();
                            String room_date = snapshot.child("roomdate").getValue().toString();
                            if(room_info.length() > 10) room_info = room_info.substring(0, 10)+"…";

//                            room_index++;
//                            holder.roomindex.setText("" + room_index);
                            holder.roomname.setText(room_name);
                            holder.roominfo.setText(room_info);
                            holder.roomperson.setText(room_person + "명");
                        }
                        else{

                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_info = snapshot.child("roominfo").getValue().toString();
                            String room_person = snapshot.child("roomperson").getValue().toString();
                            String room_cate = snapshot.child("roomcategory").getValue().toString();
                            String room_date = snapshot.child("roomdate").getValue().toString();
                            if(room_info.length() > 10) room_info = room_info.substring(0, 10)+"…";

//                            holder.roomindex.setText("" + room_index);
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
//            roomindex = itemView.findViewById(R.id.text_index);
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

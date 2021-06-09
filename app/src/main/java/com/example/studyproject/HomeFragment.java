package com.example.studyproject;

import android.app.VoiceInteractor;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class HomeFragment extends Fragment{
    private Toolbar toolbar_home;
    private RecyclerView recview;
    private View view;
    private DatabaseReference mDatabase;
    TextView text_nick;
    String nickname;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저 정보 가져오기
    String uid = user != null ? user.getUid() : null; // 로그인한 유저의 고유 uid 가져오기


    Query query = FirebaseDatabase.getInstance().getReference("study_rooms")
            .orderByChild("member/" + uid)
            .equalTo("true");


    public HomeFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        text_nick = (TextView) view.findViewById(R.id.textviewNickanme);


        recview=(RecyclerView)view.findViewById(R.id.roomrecyclerview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabase = FirebaseDatabase.getInstance().getReference();


        // 툴바 추가
        toolbar_home = (Toolbar) view.findViewById(R.id.toolbarHome);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar_home);

        ((AppCompatActivity)getActivity()).setTitle("");
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_notification_icon);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyStudyFragment mystudyfragment = new MyStudyFragment();
        FragmentTransaction transaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();



        FirebaseRecyclerOptions<MakeRoomDB> options =
                new FirebaseRecyclerOptions.Builder<MakeRoomDB>()
                        .setQuery(query, MakeRoomDB.class) // 노드 데이터 읽어오기
                        .build();

        FirebaseRecyclerAdapter<MakeRoomDB, HomeFragment.ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<MakeRoomDB, HomeFragment.ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final HomeFragment.ContactsViewHolder holder, int position, @NonNull MakeRoomDB model) {
                String roomId = getRef(position).getKey(); // 리사이클러뷰
                final String name = model.getRoomname();
                final String info = model.getRoominfo();

                // MyStudyFragment 로 데이터 보냄
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        String item_roomId = getRef(pos).getKey();

                        Bundle bundle = new Bundle(); // 번들을 통해 값 전달
                        bundle.putString("key_roomname", item_roomId);//번들에 넘길 값 저장
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        MyStudyFragment myStudyFragment = new MyStudyFragment();//프래그먼트2 선언
                        myStudyFragment.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                        transaction.replace(R.id.container, myStudyFragment);
                        transaction.commit();

                    }
                });

                // 이름 불러오기
                mDatabase.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        if (dataSnapshot.getValue(User.class) != null) {
                            User post = dataSnapshot.getValue(User.class);
                            nickname = post.getNickname();
                            text_nick.setText(nickname);
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

                mDatabase.child("study_rooms").child(roomId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("study_rooms")) {
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_honey = snapshot.child("roomhoney").getValue().toString();

                            holder.roomname.setText(room_name);
                            holder.roomhoney.setText(room_honey + "꿀");
                            holder.roomday.setText("1일째");
                        } else {
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_honey = snapshot.child("roomhoney").getValue().toString();

                            holder.roomname.setText(room_name);
                            holder.roomhoney.setText(room_honey +"꿀");
                            holder.roomday.setText("1일째");
                        }
                        if (snapshot.getValue(User.class) != null) {
                            User post = snapshot.getValue(User.class);
                            nickname = post.getNickname();
                            text_nick.setText(nickname);
                            Log.w("FireBaseData", "getData" + post.toString());
                        } else {
                            //Toast.makeText(MainActivity.this, "데이터 없음...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("FireBaseData", "loadPost:onCancelled", error.toException());
                    }
                });

            }

            @NonNull
            @Override
            public HomeFragment.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_room_design, viewGroup, false);
                HomeFragment.ContactsViewHolder viewHolder = new HomeFragment.ContactsViewHolder(view);
                return viewHolder;
            }
        };
        recview.setAdapter(adapter);
        adapter.startListening();
    }
    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView roomname, roomhoney, roomday;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            roomname = itemView.findViewById(R.id.studyroomname);
            roomhoney = itemView.findViewById(R.id.ggul);
            roomday = itemView.findViewById(R.id.day);
        }
    }

    //Fragment 변경위한 함수
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

}
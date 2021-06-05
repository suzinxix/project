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

import java.util.List;

import static android.app.Activity.RESULT_OK;

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
            .orderByChild("member/name")
            .equalTo(uid);

    Button bt_temp;

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



        // 임시 버튼 HomeFragment->MyStudyFragment
        bt_temp = (Button) view.findViewById(R.id.bt_temp);
        bt_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).replaceFragment(MyStudyFragment.newInstance()); //Fragment 변경
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

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

                // 클릭 시 희서가 만든 데이터로 이동
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SearchDetail.class);
                        intent.putExtra("Roomname", ""+name);
                        intent.putExtra("Roominfo", info);
                        startActivity(intent);
                    }
                });

                mDatabase.child("study_rooms").child(roomId).addValueEventListener(new ValueEventListener() {
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
        TextView roomname, roominfo;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            roomname = itemView.findViewById(R.id.studyroomname);
            roominfo = itemView.findViewById(R.id.ggul);
        }
    }

    //Fragment 변경위한 함수
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

}
package com.example.studyproject;

import android.app.AlertDialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment{
    private ArrayList<HomeFragment> homeFragments;
    private Toolbar toolbar_home;
    private RecyclerView recview;
    private RecyclerView.Adapter adapter;
    private View view;
    private DatabaseReference mDatabase;
    TextView text_nick;
    String nickname;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저 정보 가져오기
    String uid = user != null ? user.getUid() : null; // 로그인한 유저의 고유 uid 가져오기


    Query query = FirebaseDatabase.getInstance().getReference("study_rooms")
            .orderByChild("member/" + uid + "/joined")
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

                // 롱클릭 시 스터디룸
               holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getAdapterPosition();
                        String item_roomId = getRef(pos).getKey();

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("study_rooms").child(item_roomId).child("member").child(uid);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("중요") //제목
                                .setMessage("선택하신 스터디룸에 탈퇴하시겠습니까?")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDatabase.removeValue();
                                        recview.getAdapter().notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                        return true;
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
                        // 현재 시간 계산
                        Calendar getToday = Calendar.getInstance();
                        getToday.setTime(new Date()); // 현재 날짜

                        if (snapshot.child("member").child(uid).exists()) {
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_honey = snapshot.child("ggul").getValue().toString();
                            String room_day = snapshot.child("member/" + uid + "/joinDate/year").getValue().toString() + "-"
                                    +snapshot.child("member/" + uid + "/joinDate/month").getValue().toString() + "-"
                                    +snapshot.child("member/" + uid + "/joinDate/date").getValue().toString();

                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(room_day);
                                Calendar cmpDate = Calendar.getInstance();
                                cmpDate.setTime(date); //특정 일자

                                long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
                                long diffDays = diffSec / (24*60*60); //일자수 차이
                                holder.roomday.setText(diffDays + "일째");
                            } catch (Exception e) {
                                Log.v("알림", "데이터 못 읽음");
                            }
                            holder.roomname.setText(room_name);
                            holder.roomhoney.setText(room_honey + "꿀");
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
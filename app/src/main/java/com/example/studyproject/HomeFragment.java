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

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // ???????????? ?????? ?????? ????????????
    String uid = user != null ? user.getUid() : null; // ???????????? ????????? ?????? uid ????????????


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

        // ?????? ??????
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
                        .setQuery(query, MakeRoomDB.class) // ?????? ????????? ????????????
                        .build();

        FirebaseRecyclerAdapter<MakeRoomDB, HomeFragment.ContactsViewHolder> adapter = new FirebaseRecyclerAdapter<MakeRoomDB, HomeFragment.ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final HomeFragment.ContactsViewHolder holder, int position, @NonNull MakeRoomDB model) {
                String roomId = getRef(position).getKey(); // ??????????????????


                final String name = model.getRoomname();
                final String info = model.getRoominfo();

                // MyStudyFragment ??? ????????? ??????
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        String item_roomId = getRef(pos).getKey();

                        Bundle bundle = new Bundle(); // ????????? ?????? ??? ??????
                        bundle.putString("key_roomname", item_roomId);//????????? ?????? ??? ??????
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        MyStudyFragment myStudyFragment = new MyStudyFragment();//???????????????2 ??????
                        myStudyFragment.setArguments(bundle);//????????? ???????????????2??? ?????? ??????
                        transaction.replace(R.id.container, myStudyFragment);
                        transaction.commit();
                    }
                });

                // ????????? ??? ????????????
               holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getAdapterPosition();
                        String item_roomId = getRef(pos).getKey();

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("study_rooms").child(item_roomId).child("member").child(uid);

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("??????") //??????
                                .setMessage("???????????? ??????????????? ?????????????????????????")
                                .setPositiveButton("???", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDatabase.removeValue();
                                        recview.getAdapter().notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.show();
                        return true;
                    }
                });

                // ?????? ????????????
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
                            //Toast.makeText(MainActivity.this, "????????? ??????...", Toast.LENGTH_SHORT).show();
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
                        // ?????? ?????? ??????
                        Calendar getToday = Calendar.getInstance();
                        getToday.setTime(new Date()); // ?????? ??????

                        if (snapshot.child("member").child(uid).exists()) {
                            String room_name = snapshot.child("roomname").getValue().toString();
                            String room_honey = snapshot.child("ggul").getValue().toString();
                            String room_day = snapshot.child("member/" + uid + "/joinDate/year").getValue().toString() + "-"
                                    +snapshot.child("member/" + uid + "/joinDate/month").getValue().toString() + "-"
                                    +snapshot.child("member/" + uid + "/joinDate/date").getValue().toString();

                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(room_day);
                                Calendar cmpDate = Calendar.getInstance();
                                cmpDate.setTime(date); //?????? ??????

                                long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
                                long diffDays = diffSec / (24*60*60); //????????? ??????
                                holder.roomday.setText(diffDays + "??????");
                            } catch (Exception e) {
                                Log.v("??????", "????????? ??? ??????");
                            }
                            holder.roomname.setText(room_name);
                            holder.roomhoney.setText(room_honey + "???");
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

    //Fragment ???????????? ??????
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

}
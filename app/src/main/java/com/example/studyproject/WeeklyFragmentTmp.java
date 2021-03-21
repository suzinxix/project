package com.example.studyproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WeeklyFragmentTmp extends Fragment {
    private View weeklyView;
    private RecyclerView myWeeklyList;
    private DatabaseReference WeeklyRef, WeekRef;

    public WeeklyFragmentTmp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        weeklyView = (View) inflater.inflate(R.layout.fragment_weekly_tmp, container, false);

        myWeeklyList = (RecyclerView) weeklyView.findViewById(R.id.recyclerviewWeekly);
        myWeeklyList.setLayoutManager(new LinearLayoutManager(getContext()));

        WeeklyRef = FirebaseDatabase.getInstance().getReference().child("weekly");
        WeekRef = FirebaseDatabase.getInstance().getReference().child("weekly");

        return weeklyView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<WeeklyDB> options = new FirebaseRecyclerOptions.Builder<WeeklyDB>()
                .setQuery(WeeklyRef, WeeklyDB.class).build();

        FirebaseRecyclerAdapter<WeeklyDB, WeeklyViewHolder> adapter
                = new FirebaseRecyclerAdapter<WeeklyDB, WeeklyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull WeeklyViewHolder holder, int position, @NonNull WeeklyDB model) {
                String week = getRef(position).getKey();

                WeekRef.child(week).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("weekly")) {
                            String week_num = snapshot.child("week").getValue().toString();
                            String week_todo = snapshot.child("todo").getValue().toString();

                            holder.tv_week.setText(week_num);
                            holder.tv_todo.setText(week_todo);
                        } else {
                            String week_num = snapshot.child("week").getValue().toString();
                            String week_todo = snapshot.child("todo").getValue().toString();

                            holder.tv_week.setText(week_num);
                            holder.tv_todo.setText(week_todo);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_layout, parent, false);
                WeeklyViewHolder viewHolder = new WeeklyViewHolder(view);
                return viewHolder;
            }
        };

        myWeeklyList.setAdapter(adapter);
        adapter.startListening();
    }

    public class WeeklyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_week, tv_todo;
        ImageButton ibt_camera, ibt_timer;
        TimerFragment fragment_timer;

        public WeeklyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_week = (TextView) itemView.findViewById(R.id.textViewWeek);
            tv_todo = (TextView) itemView.findViewById(R.id.textViewTodo);
            fragment_timer = new TimerFragment();

            ibt_camera = (ImageButton)itemView.findViewById(R.id.imageButtonCamera);
            ibt_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //dispatchTakePictureIntent();
                        }
                    };

                    DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //doTakeAlbumAction();
                        }
                    };

                    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    };
                    new AlertDialog.Builder(getContext())
                            .setTitle("업로드할 이미지 선택")
                            .setPositiveButton("사진촬영", cameraListener)
                            .setNeutralButton("앨범선택", albumListener)
                            .setNegativeButton("취소", cancelListener)
                            .show();
                }
            });

            ibt_timer = (ImageButton)itemView.findViewById(R.id.imageButtonTimer);
            ibt_timer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerTabs, fragment_timer).commit();
                }
            });
        }
    }
}

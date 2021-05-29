package com.example.studyproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserStudyRoomAdapter extends RecyclerView.Adapter<UserStudyRoomAdapter.myviewholder>{
    private List<UserStudyRoom> userStudyRooms = new ArrayList<>();

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // 뷰 홀더 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_room_design, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) { // 뷰 홀더 재활용
        UserStudyRoom currentRoom = userStudyRooms.get(position);
        holder.studyroomname.setText(currentRoom.getRoomtitle());
        holder.ggulsum.setText(String.valueOf(currentRoom.getRoomggul()));
        holder.daystart.setText(String.valueOf(currentRoom.getRoomstartday()));
    }

    @Override
    public int getItemCount() { // 아이템 개수 조회
        return userStudyRooms.size();
    }

    public void setUserStudyRooms(List<UserStudyRoom> userStudyRooms) {
        this.userStudyRooms = userStudyRooms;
        notifyDataSetChanged();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView studyroomname, ggulsum, daystart;

        public myviewholder(@NonNull View itemview){
            super(itemview);
            this.studyroomname=itemview.findViewById(R.id.studyroomname);
            this.ggulsum=itemview.findViewById(R.id.ggul);
            daystart=itemview.findViewById(R.id.day);
        }
    }
}

package com.example.studyproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRoomFragmentAdapter extends RecyclerView.Adapter<MyRoomFragmentAdapter.myviewholder>{
    ArrayList<UserStudyRoomDB> dataholder;

    public MyRoomFragmentAdapter(ArrayList<UserStudyRoomDB> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // 뷰 홀더 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_room_design, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) { // 뷰 홀더 재활용
        holder.studyroomname.setText(dataholder.get(position).getStudyroomname());
        //holder.ggul.setText(dataholder.get(position).getGgul());
        //holder.daystart.setText(dataholder.get(position).getDaystart());
    }

    @Override
    public int getItemCount() { // 아이템 개수 조회
        return dataholder == null ? 0 :dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        TextView studyroomname, ggul, daystart;

        public myviewholder(@NonNull View itemview){
            super(itemview);
            this.studyroomname=itemview.findViewById(R.id.studyroomname);
            this.ggul=itemview.findViewById(R.id.ggul);
            daystart=itemview.findViewById(R.id.day);
        }
    }
}

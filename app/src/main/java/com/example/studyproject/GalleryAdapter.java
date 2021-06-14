package com.example.studyproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context mContext;
    private List<GalleryDB> mUploads;
    private DatabaseReference nicknameRef;
    private String nickname;
    private List<String> listnickname;

    public GalleryAdapter(Context context, List<GalleryDB> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_layout, parent, false);

        nicknameRef = FirebaseDatabase.getInstance().getReference().child("users");
        listnickname = new ArrayList<>();
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GalleryDB uploadCurrent = mUploads.get(position);
        String id = uploadCurrent.getUserId();

        holder.tv_photoText.setText(uploadCurrent.getPhotoText());

        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.iv_photo);

        nicknameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nickname = snapshot.child(id).child("nickname").getValue().toString();
                holder.tv_UserText.setText(nickname);
                listnickname.add(nickname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_photo;
        public TextView tv_photoText;
        public TextView tv_UserText;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_photo = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
            this.tv_photoText = (TextView) itemView.findViewById(R.id.textViewPhotoText);
            this.tv_UserText = (TextView) itemView.findViewById(R.id.textViewUserText);
        }
    }
}

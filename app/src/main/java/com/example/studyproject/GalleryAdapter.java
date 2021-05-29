package com.example.studyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context mContext;
    private List<GalleryDB> mUploads;

    public GalleryAdapter(Context context, List<GalleryDB> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_layout, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        GalleryDB uploadCurrent = mUploads.get(position);
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.iv_photo);
//        Glide.with(holder.itemView)
//                .load(arrayList.get(position).getProfile())
//                .into(holder.);
    }

    @Override
    public int getItemCount() {
        return 0;
    } //return mUploads.size();

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_photo;
        TextView tv_photoText;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_photo = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
            this.tv_photoText = (TextView) itemView.findViewById(R.id.textViewPhotoText);
        }
    }
}

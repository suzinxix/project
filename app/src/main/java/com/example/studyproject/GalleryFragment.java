package com.example.studyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private RecyclerView GalleryPhotoList;
    private StorageReference storageRef;
    private GalleryAdapter galleryAdapter;

    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;

    private List<GalleryDB> mUploads;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View galleryView = (View) inflater.inflate(R.layout.fragment_gallery, container, false);

        GalleryPhotoList = (RecyclerView) galleryView.findViewById(R.id.recyclerviewGallery);
        GalleryPhotoList.setHasFixedSize(true);
        GalleryPhotoList.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mUploads = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(getContext(), mUploads);
        GalleryPhotoList.setAdapter(galleryAdapter);
        //galleryAdapter.setOnItemClickListener(get);

        FirebaseStorage fs = FirebaseStorage.getInstance();
        StorageReference imagesRef = fs.getReference("gallery");
//        Glide.with(this)
//                .load(imagesRef)
//                .into(profileImgView);

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GalleryDB galleryDB = postSnapshot.getValue(GalleryDB.class);
                    galleryDB.setKey(postSnapshot.getKey());
                    mUploads.add(galleryDB);
                }
                galleryAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        return galleryView;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerOptions<GalleryDB> options = new FirebaseRecyclerOptions.Builder<GalleryDB>().build();
//
//        FirebaseRecyclerAdapter<GalleryDB, GalleryViewHolder> adapter
//                = new FirebaseRecyclerAdapter<GalleryDB, GalleryViewHolder>(options) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull GalleryViewHolder holder, int position, @NonNull GalleryDB model) {
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                storageRef = storage.getReference("gallery/child.jpg");
////                GlideApp.with(holder.itemView)
////                        .load(storageRef)
////                        .into(holder.iv_photo);
////                        //.thumbnail(0.5f)
//            }
//
//            @NonNull
//            @Override
//            public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_layout, parent, false);
//                return new GalleryViewHolder(view);
//            }
//
//
//        };
//
//        GalleryPhotoList.setAdapter(adapter);
//        adapter.startListening();
//    }

//    public class GalleryViewHolder extends RecyclerView.ViewHolder {
//        ImageView iv_photo;
//        TextView tv_phototext;
//
//        public GalleryViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            iv_photo = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
//            tv_phototext = (TextView) itemView.findViewById(R.id.textViewPhotoText);
//            //tv_phototext.setText();
//        }
//    }




}

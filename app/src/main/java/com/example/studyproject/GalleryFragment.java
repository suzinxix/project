package com.example.studyproject;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private RecyclerView GalleryPhotoList;
    private GalleryAdapter galleryAdapter;

    private DatabaseReference mDatabaseRef;
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


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gallery_url");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GalleryDB galleryDB = postSnapshot.getValue(GalleryDB.class);
                    galleryDB.setKey(postSnapshot.getKey());
                    mUploads.add(galleryDB);
                }

                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return galleryView;
    }

}

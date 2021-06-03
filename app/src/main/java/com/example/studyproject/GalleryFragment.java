package com.example.studyproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 로그인한 유저 정보 가져오기
        String uid = user != null ? user.getUid() : null; // 로그인한 유저의 고유 uid 가져오기

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("gallery_url");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GalleryDB galleryDB = postSnapshot.getValue(GalleryDB.class);
                    if (galleryDB.getUserId().equals(uid)) {
                        galleryDB.setKey(postSnapshot.getKey());
                        mUploads.add(galleryDB);
                    }
                }

                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return galleryView;
    }

}

package com.example.studyproject;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class GalleryFragment extends Fragment {
    ImageView iv_gallery1;


    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_gallery, container, false);

        iv_gallery1 = view.findViewById(R.id.imageViewGallery1);

        clickLoad(view);
        return view;
    }

    public void clickLoad(View view) {

        //Firebase Storage에 저장되어 있는 이미지 파일 읽어오기

        //1. Firebase Storeage관리 객체 얻어오기
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        //2. 최상위노드 참조 객체 얻어오기
        StorageReference rootRef = firebaseStorage.getReference();

        //읽어오길 원하는 파일의 참조객체 얻어오기
        StorageReference imgRef = rootRef.child("gallery/000010.png");
        //하위 폴더가 있다면 폴더명까지 포함하여
        //imgRef= rootRef.child("photo/000010.jng");

        if (imgRef != null) {
            //참조객체로 부터 이미지의 다운로드 URL을 얻어오기
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //다운로드 URL이 파라미터로 전달되어 옴.
                    Glide.with(Objects.requireNonNull(getActivity())).load(uri).into(iv_gallery1);
                }
            });

        }

    }
}

package com.example.studyproject;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class image extends AppCompatActivity {
    private StorageReference storageRef;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView img_test = findViewById(R.id.img_test);
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-test-1-35648.appspot.com/");
        storageRef = storage.getReference();
        storageRef.child("gallery/frontend.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시

                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(img_test);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
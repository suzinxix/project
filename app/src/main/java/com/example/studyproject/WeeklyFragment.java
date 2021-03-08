package com.example.studyproject;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class WeeklyFragment extends Fragment implements View.OnClickListener {
    final private static String TAG = "태그명";
    ImageButton ibt_camera, ibt_timer;
    ImageView iv_photo;
    String mCurrentPhotoPath;
    String absoultePath;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    TimerFragment fragment_timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_weekly, container, false);

        ibt_camera = (ImageButton)view.findViewById(R.id.imageButtonCamera);
        ibt_camera.setOnClickListener(this);

        fragment_timer = new TimerFragment();
        ibt_timer = (ImageButton)view.findViewById(R.id.imageButtonTimer);
        ibt_timer.setOnClickListener(this);

        iv_photo = view.findViewById(R.id.imageViewTempGallery); //수정

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else { Log.d(TAG, "권한 설정 요청");
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonCamera:
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dispatchTakePictureIntent();
                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
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
                break;
            case R.id.imageButtonTimer:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerTabs, fragment_timer).commit();
                break;
        }
    }

    //권한요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case PICK_FROM_ALBUM: {
                    if(resultCode == RESULT_OK)
                    {
                        try{
                            InputStream in = getActivity().getContentResolver().openInputStream(data.getData());

                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();

                            iv_photo.setImageBitmap(img);
                        } catch(Exception e) { }
                    }
                    break;
                }

                case PICK_FROM_CAMERA: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getApplicationContext().getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) { iv_photo.setImageBitmap(bitmap);
                                }
                            } catch (IOException e) { e.printStackTrace(); }
                        }
                        else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) {
                                    iv_photo.setImageBitmap(bitmap);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } break;
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    // 사진 촬영 후 썸네일만 띄워줌. 이미지를 파일로 저장해야 함
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName, ".jpg", storageDir );
        mCurrentPhotoPath = image.getAbsolutePath(); return image;
    }

    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getApplicationContext().getPackageManager()) != null) {
            File photoFile = null;
            try { photoFile = createImageFile();
            } catch (IOException ex) { }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.example.studyproject.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
            }
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        // SmartWheel 폴더를 생성하여 이미지를 저장하는 방식이다.
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
        File directory_SmartWheel = new File(dirPath);
        if (!directory_SmartWheel.exists()) // SmartWheel 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
            directory_SmartWheel.mkdir();
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;
        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            if (getActivity() != null) {
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

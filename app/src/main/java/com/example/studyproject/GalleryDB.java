package com.example.studyproject;

import com.google.firebase.database.Exclude;

public class GalleryDB {
    public String photoText;
    private String mImageUrl;
    private String mKey;

    public GalleryDB () {

    }

    public GalleryDB (String photoText, String imageUrl) {
        this.photoText = photoText;
        mImageUrl = imageUrl;
    }

    public String getPhotoText() { return photoText; }
    public void setPhotoText(String todo) { this.photoText = photoText; }

    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}

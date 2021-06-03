package com.example.studyproject;

import com.google.firebase.database.Exclude;

public class GalleryDB {
    public String mphotoText;
    private String mImageUrl;
    private String mKey;

    public GalleryDB () {

    }

    public GalleryDB (String photoText, String imageUrl) {
        if (photoText.trim().equals("")) {
            photoText = "No Name";
        } //

        mphotoText = photoText;
        mImageUrl = imageUrl;
    }

    public String getPhotoText() { return mphotoText; }
    public void setPhotoText(String photoText) { mphotoText = photoText; }

    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() { return mKey; }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}

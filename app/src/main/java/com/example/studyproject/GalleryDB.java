package com.example.studyproject;

import com.google.firebase.database.Exclude;

public class GalleryDB {
    public String photoText;
    private String mImageUrl;
    private String mUserId;
    private String mRoomname;
    private String mKey;

    public GalleryDB () {

    }

    public GalleryDB (String photoText, String imageUrl, String userId, String roomname) {
        if (photoText.trim().equals("")) {
            photoText = "No Name";
        } //

        this.photoText = photoText;
        mImageUrl = imageUrl;
        mUserId = userId;
        mRoomname = roomname;
    }

    public String getPhotoText() { return photoText; }
    public void setPhotoText(String photoText) { this.photoText = photoText; }

    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getUserId() { return mUserId; }
    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getRoomname() {
        return mRoomname;
    }
    public void setRoomname(String roomname) {
        mRoomname = roomname;
    }

    @Exclude
    public String getKey() { return mKey; }
    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}

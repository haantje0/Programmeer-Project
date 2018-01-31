package com.example.lex.collectorsapp;

/**
 * Created by lex on 28/01/2018.
 */

class FriendItem {

    private String userId;
    private String userName;
    private String pictureURL;

    void setUserId(String userId) {
        this.userId = userId;
    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    void setPictureURL(String picURL) {
        this.pictureURL = picURL;
    }

    String getUserId() {
        return userId;
    }

    String getUserName() {
        return userName;
    }

    String getPictureURL() {
        return pictureURL;
    }
}

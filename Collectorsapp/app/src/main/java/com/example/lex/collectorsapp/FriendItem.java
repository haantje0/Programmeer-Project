package com.example.lex.collectorsapp;

/**
 * Created by lex on 28/01/2018.
 */

public class FriendItem {

    String userId;
    String userName;
    String pictureURL;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPictureURL(String picURL) {
        this.pictureURL = picURL;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPictureURL() {
        return pictureURL;
    }
}

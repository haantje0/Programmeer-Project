package com.example.lex.collectorsapp;

/**
 * Created by Lex de Haan on 28/01/2018.
 *
 * This class saves information from a Facebook friend.
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

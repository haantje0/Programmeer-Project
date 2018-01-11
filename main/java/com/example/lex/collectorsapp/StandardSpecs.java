package com.example.lex.collectorsapp;

import android.graphics.Bitmap;

/**
 * Created by lex on 1/11/2018.
 */

public class StandardSpecs {

    public String title;
    public String description;
    public Bitmap image;

    public StandardSpecs() {}

    public StandardSpecs(String Title, String Description){
        title = Title;
        description = Description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }
}

package com.example.lex.collectorsapp;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by lex on 1/11/2018.
 */

class Specs {

    public String name;
    public String description;
    public HashMap<String, String> extraSpecs = new HashMap<>();
    public String image;

    public Specs() {}

    public Specs(String Name, String Description) {
        setName(Name);
        setDescription(Description);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExtraSpecs(HashMap<String, String> extraSpecs) {
        this.extraSpecs = extraSpecs;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, String> getExtraSpecs() {
        return extraSpecs;
    }

    public String getImage() {
        return image;
    }
}

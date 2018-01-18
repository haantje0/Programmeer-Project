package com.example.lex.collectorsapp;

import android.graphics.Bitmap;

/**
 * Created by lex on 1/11/2018.
 */

class Specs {

    public String name;


    public String description;
    public String date;
    public String amount;
    public String extraSpecs;
    public Bitmap image;

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

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setExtraSpecs(String extraSpecs) {
        this.extraSpecs = extraSpecs;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getExtraSpecs() {
        return extraSpecs;
    }

    public String getAmount() {
        return amount;
    }

    public Bitmap getImage() {
        return image;
    }
}

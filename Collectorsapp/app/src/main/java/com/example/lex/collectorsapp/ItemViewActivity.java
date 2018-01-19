package com.example.lex.collectorsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    Specs specs = new Specs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        specs.setName(intent.getStringExtra("Name"));
        specs.setDescription(intent.getStringExtra("Description"));
        specs.setDate(intent.getStringExtra("Date"));
        specs.setAmount(intent.getStringExtra("Amount"));
        specs.setExtraSpecs(intent.getStringExtra("ExtraSpecs"));
        specs.setImage(intent.getStringExtra("Image"));

        makeTheView();
    }

    public void makeTheView() {
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        TextView textViewAmount = (TextView) findViewById(R.id.textViewAmount);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewItemView);
        TextView textViewExtraSpec = (TextView) findViewById(R.id.textViewExtraSpec);

        textViewName.setText(specs.getName());
        textViewDescription.setText(specs.getDescription());
        textViewDate.setText("Date: " + specs.getDate());
        textViewAmount.setText("Amount: " + specs.getAmount());
        textViewExtraSpec.setText("Extra Spec: " + specs.getExtraSpecs());

        String image = specs.getImage();
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);

    }

}

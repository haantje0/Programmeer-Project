package com.example.lex.collectorsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lex on 1/16/2018.
 */

public class ItemViewActivity extends AppCompatActivity {

    Specs specs = new Specs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        specs.setName(intent.getStringExtra("Name"));
        specs.setDescription(intent.getStringExtra("Description"));
        specs.setImage(intent.getStringExtra("Image"));
        specs.setExtraSpecs((HashMap<String, String>) intent.getSerializableExtra("ExtraSpecs"));

        makeTheView();
    }

    @SuppressLint("SetTextI18n")
    public void makeTheView() {
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        ImageView imageView = findViewById(R.id.imageViewItemView);

        textViewName.setText(specs.getName());
        textViewDescription.setText(specs.getDescription());

        String image = specs.getImage();
        if (image.length() == 0) {
            imageView.setImageResource(R.drawable.ic_photo_black_24dp);
        }
        else {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }

        LinearLayout linearLayout = findViewById(R.id.linearLayoutExtraSpecsItemView);
        HashMap<String, String> extraSpecs = specs.getExtraSpecs();

        Set mapSet = extraSpecs.entrySet();
        for (Object aMapSet : mapSet) {
            Map.Entry mapEntry = (Map.Entry) aMapSet;
            String keyValue = (String) mapEntry.getKey();
            String value = (String) mapEntry.getValue();

            LayoutInflater layoutInflater =
                    (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.spec_itemview, null);

            TextView textView = addView.findViewById(R.id.textViewItemViewExtraSpec);
            textView.setText(keyValue + ": " + value);

            linearLayout.addView(addView);
        }
    }
}

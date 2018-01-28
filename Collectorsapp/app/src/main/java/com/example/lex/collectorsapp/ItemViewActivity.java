package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
        specs.setImage(intent.getStringExtra("Image"));
        specs.setExtraSpecs((HashMap<String, String>) intent.getSerializableExtra("ExtraSpecs"));

        makeTheView();
    }

    public void makeTheView() {
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewItemView);
        //TextView textViewExtraSpec = (TextView) findViewById(R.id.textViewExtraSpec);

        textViewName.setText(specs.getName());
        textViewDescription.setText(specs.getDescription());
        //textViewExtraSpec.setText("Extra Spec: " + specs.getExtraSpecsFromDB());

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
        Iterator mapIterator = mapSet.iterator();
        while (mapIterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) mapIterator.next();
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

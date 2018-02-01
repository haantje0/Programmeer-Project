package com.example.lex.collectorsapp;

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
 * Created by Lex de Haan on 1/16/2018.
 *
 * This activity shows the given item and its specifications.
 */

public class ItemViewActivity extends AppCompatActivity {

    // make the specifications
    Specs specs = new Specs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // get all the information from the intent en put it in specs
        Intent intent = getIntent();
        specs.setName(intent.getStringExtra("Name"));
        specs.setDescription(intent.getStringExtra("Description"));
        specs.setImage(intent.getStringExtra("Image"));
        specs.setExtraSpecs((HashMap<String, String>) intent.getSerializableExtra("ExtraSpecs"));

        // put all information in views
        makeTheView();
    }

    /**
     * The following functions set all the item specifications in the view
     */

    // set all the views
    public void makeTheView() {
        setTextViewTitle();
        setTextViewDescription();
        setImageView();
        setLinearLayoutExtraSpecs();
    }

    public void setTextViewTitle() {
        TextView textViewName = findViewById(R.id.textViewName);
        textViewName.setText(specs.getName());
    }

    public void setTextViewDescription() {
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        textViewDescription.setText(specs.getDescription());
    }

    private void setImageView() {
        ImageView imageView = findViewById(R.id.imageViewItemView);
        String image = specs.getImage();

        // check if there is an image
        if (image.length() == 0) {
            imageView.setImageResource(R.drawable.ic_photo_black_24dp);
        }
        else {
            // decode the string to a bitmap
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }
    }

    private void setLinearLayoutExtraSpecs() {
        LinearLayout linearLayout = findViewById(R.id.linearLayoutExtraSpecsItemView);
        HashMap<String, String> extraSpecs = specs.getExtraSpecs();

        // get all keys and values from the extra specifications
        Set mapSet = extraSpecs.entrySet();
        for (Object aMapSet : mapSet) {
            Map.Entry mapEntry = (Map.Entry) aMapSet;
            String keyValue = (String) mapEntry.getKey();
            String value = (String) mapEntry.getValue();

            // put the extra specifications into a layout
            LayoutInflater layoutInflater =
                    (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.spec_itemview, null);

            TextView textView = addView.findViewById(R.id.textViewItemViewExtraSpec);
            textView.setText(keyValue + ": " + value);

            linearLayout.addView(addView);
        }
    }
}

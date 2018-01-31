package com.example.lex.collectorsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lex de Haan on 1/11/2018.
 *
 * This class puts items with title, description and picture in a listview.
 */

public class ItemViewAdapter extends ArrayAdapter<Specs> {

    // constructor
    ItemViewAdapter(Context context, ArrayList<Specs> specs) {
        super(context, R.layout.itemslayout, specs);
    }

    // inflate the data into the listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get the layout inflater
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.itemslayout, parent, false);

        // put the name in the view
        String title = getItem(position).getName();
        TextView titleTextView = theView.findViewById(R.id.textViewTitle);
        titleTextView.setText(title);

        // put the description in the view
        String description = getItem(position).getDescription();
        TextView descriptionTextView = theView.findViewById(R.id.textViewDescription);
        descriptionTextView.setText(description);

        // put the image in the view
        String image = getItem(position).getImage();
        ImageView mImageView = theView.findViewById(R.id.imageViewItem);
        if (image.length() == 0) {
            mImageView.setImageResource(R.drawable.ic_photo_black_24dp);
        } else {
            // decode the string to a bitmap
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            mImageView.setImageBitmap(decodedByte);
        }

        return theView;
    }
}

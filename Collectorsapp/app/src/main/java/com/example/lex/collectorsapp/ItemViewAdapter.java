package com.example.lex.collectorsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by lex on 1/11/2018.
 */

public class ItemViewAdapter extends ArrayAdapter<Specs> {
    public ItemViewAdapter(Context context, List<Specs> specs) {
        super(context, R.layout.itemslayout, (List<Specs>) specs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.itemslayout, parent, false);

        // put the name in the view
        String title = getItem(position).getName();
        TextView titleTextView = (TextView) theView.findViewById(R.id.textViewTitle);
        titleTextView.setText(title);

        // put the description in the view
        String description = getItem(position).getDescription();
        TextView descriptionTextView = (TextView) theView.findViewById(R.id.textViewDescription);
        descriptionTextView.setText(description);

        // put the image in the view
        Bitmap image = getItem(position).getImage();
        ImageView mImageView = (ImageView) theView.findViewById(R.id.imageViewItem);
        mImageView.setImageBitmap(image);

        return theView;
    }
}

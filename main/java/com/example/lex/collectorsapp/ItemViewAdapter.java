package com.example.lex.collectorsapp;

import android.content.Context;
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

public class ItemViewAdapter extends ArrayAdapter<StandardSpecs> {
    public ItemViewAdapter(Context context, List<StandardSpecs> standardspecs) {
        super(context, R.layout.itemslayout, (List<StandardSpecs>) standardspecs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.itemslayout, parent, false);

        // put the username in the view
        String title = getItem(position).getTitle();
        TextView titleTextView = (TextView) theView.findViewById(R.id.textViewTitle);
        titleTextView.setText(title);

        // set if the user is eating or not
        String description = getItem(position).getDescription();
        TextView descriptionTextView = (TextView) theView.findViewById(R.id.textViewDescription);
        descriptionTextView.setText(description);

        return theView;
    }
}

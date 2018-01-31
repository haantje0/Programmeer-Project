package com.example.lex.collectorsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lex de Haan on 28/01/2018.
 *
 * This class puts the Facebook friends into a listview.
 *
 * The profile picture is downloaded asynchronous.
 */

public class FriendsAdapter extends ArrayAdapter<FriendItem> {

    // constructor
    FriendsAdapter(Context context, ArrayList<FriendItem> friendList) {
        super(context, R.layout.itemslayout, (ArrayList<FriendItem>) friendList);
    }

    // inflate the data into the listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // get the layout inflater
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.friendslayout, parent, false);

        // put the name in the view
        String name = getItem(position).getUserName();
        TextView nameTextView = (TextView) theView.findViewById(R.id.textViewName);
        nameTextView.setText(name);

        // put the picture in the view
        String pictureURL = getItem(position).getPictureURL();
        ImageView mImageView = (ImageView) theView.findViewById(R.id.imageViewFriend);

        if (pictureURL != null) {
            // download the picture
            mImageView.setTag(pictureURL);
            new DownloadImageTask().execute(mImageView);
        }
        else {
            mImageView.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
        }

        return theView;
    }

    // download the image asynchronous
    public class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return download_Image((String)imageView.getTag());
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        private Bitmap download_Image(String url) {

            Bitmap bmp;
            try{
                URL ulrn = new URL(url);
                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;

            }catch(Exception ignored){}
            return null;
        }
    }
}

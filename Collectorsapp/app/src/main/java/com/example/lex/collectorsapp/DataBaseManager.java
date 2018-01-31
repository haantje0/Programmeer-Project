package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Lex de Haan on 1/16/2018.
 *
 * This Helper class handles all calls with the Firebase database.
 *
 * It takes information from the database and stores information to it.
 *
 * When information is asked from the database, this helper will also put the information in the
 * right view.
 *
 * Before using the helper it always has to be initialized via a constructor. This will make sure
 * that the right collections are taken when you try to view the collections of a friend.
 */

class DatabaseManager {

    // make a database reference
    private DatabaseReference mDatabase;

    // make a facebook ID variable
    private String facebookID;

    // set the database of the user
    void setDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        facebookID = AccessToken.getCurrentAccessToken().getUserId();
    }

    // set the database from a friend
    void setFriendDatabase(String FacebookID) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        facebookID = FacebookID;
    }

    /**
     * The following functions get information from the database.
     */

    void getCollectionsFromDB(final Context context, final ListView listView) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // make the collections arraylist
                ArrayList<String> collections = new ArrayList<>();

                // get the right data from the database and store it in the arraylist
                for (DataSnapshot collection : dataSnapshot.child(facebookID).getChildren()) {
                        collections.add(collection.getKey());
                }

                // sort the data alphabetically
                Collections.sort(collections, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                // inflate the collections in the listview
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.collectionlayout, collections);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }};
        mDatabase.addValueEventListener(postListener);
    }

    void getItemsFromDB(final Context context, final ListView listView, final String Collection) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // make the Specs arraylist
                ArrayList<Specs> specsList = new ArrayList<>();

                // get the right data from the database and store it in the arraylist
                for (DataSnapshot item : dataSnapshot.child(facebookID).child(Collection).child(Collection).getChildren()){
                        specsList.add(item.getValue(Specs.class));
                }

                // sort the data alphabetically
                Collections.sort(specsList, new Comparator<Specs>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public int compare(Specs ed1, Specs ed2) {
                        return (ed1.getName().compareToIgnoreCase(ed2.getName()));
                    }
                });

                // inflate the items in the listview
                ItemViewAdapter adapter = new ItemViewAdapter(context, specsList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }};
        mDatabase.addValueEventListener(postListener);
    }

    void getExtraSpecsFromDB(final Context context,
                             final LinearLayout linearLayout, final String collection) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get the extra specifications from the database
                for (DataSnapshot spec : dataSnapshot.child(facebookID).child(collection)
                        .child(collection + "Specs").getChildren()) {

                    // inflate the views in the list
                    LayoutInflater layoutInflater =
                            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.spec_textinput, null);

                    // add the edittext to the linearlayout
                    TextInputLayout textInputLayout = addView.findViewById(R.id.textInputLayoutAddItem);
                    EditText editText = addView.findViewById(R.id.editTextSpec);
                    textInputLayout.setHint(spec.getKey());

                    // set the right input type
                    if (spec.getValue().equals("Number")) {
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }

                    linearLayout.addView(addView);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }};
        mDatabase.addListenerForSingleValueEvent(postListener);
    }

    /**
     * The following functions add information to the database.
     */

    void addCollectionToDB(Context context, String title, HashMap<String, String> extraSpecs) {
        // put the new collection and its extra specifications in the database
        mDatabase.child(facebookID).child(title).child(title + "Specs").setValue(extraSpecs);
        mDatabase.child(facebookID).child(title).child(title).setValue(title);

        // go back to collections
        Intent intent = new Intent(context, CollectionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    // add values to the database
    void addItemToDB(Context context, Specs specs, String collection) {
        // put the new item in the database
        mDatabase.child(facebookID).child(collection).child(collection).child(specs.getName()).setValue(specs);

        // go back to the collection
        Intent intent = new Intent(context, CollectionViewActivity.class);
        intent.putExtra("Collection", collection);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * The following functions delete information from the database.
     */

    public void deleteCollectionFromDB(String collection) {
        mDatabase.child(facebookID).child(collection).removeValue();
    }

    public void deleteItemFromDB(String collection, String item) {
        mDatabase.child(facebookID).child(collection).child(collection).child(item).removeValue();
    }
}


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
 * Created by lex on 1/16/2018.
 */

class DatabaseManager {

    private DatabaseReference mDatabase;

    private String facebookID;

    // set database instance
    void setDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        facebookID = AccessToken.getCurrentAccessToken().getUserId();
    }

    void setFriendDatabase(String facebookID) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        this.facebookID = facebookID;
    }

    void getCollectionsFromDB(final Context context, final ListView listView) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // make the Specs arraylist
                ArrayList<String> collections = new ArrayList<>();

                // get the right data and store it in the arraylist
                for (DataSnapshot collection : dataSnapshot.child(facebookID).getChildren()) {
                        collections.add(collection.getKey());
                }

                Collections.sort(collections, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                // inflate the eatlist in the listview
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.collectionlayout, collections);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }};
        mDatabase.addValueEventListener(postListener);
    }

    // get information form the database
    void getItemsFromDB(final Context context, final ListView listView, final String Collection) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // make the Specs arraylist
                ArrayList<Specs> specsList = new ArrayList<>();

                // get the right data and store it
                for (DataSnapshot item : dataSnapshot.child(facebookID).child(Collection).child(Collection).getChildren()){
                        specsList.add(item.getValue(Specs.class));
                }

                Collections.sort(specsList, new Comparator<Specs>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public int compare(Specs ed1, Specs ed2) {
                        return (ed1.getName().compareToIgnoreCase(ed2.getName()));
                    }
                });

                // inflate the eatlist in the listview
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
                for (DataSnapshot spec : dataSnapshot.child(facebookID).child(collection)
                        .child(collection + "Specs").getChildren()) {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.spec_textinput, null);

                    TextInputLayout textInputLayout = addView.findViewById(R.id.textInputLayoutAddItem);
                    EditText editText = addView.findViewById(R.id.editTextSpec);
                    textInputLayout.setHint(spec.getKey());
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

    void addCollectionToDB(Context context, String title, HashMap<String, String> extraSpecs) {
        mDatabase.child(facebookID).child(title).child(title + "Specs").setValue(extraSpecs);
        mDatabase.child(facebookID).child(title).child(title).setValue(title);

        // go back to collections
        Intent intent = new Intent(context, CollectionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    // add values to the database
    void addItemToDB(Context context, Specs specs, String collection) {
        mDatabase.child(facebookID).child(collection).child(collection).child(specs.getName()).setValue(specs);

        // go back to the collection
        Intent intent = new Intent(context, CollectionViewActivity.class);
        intent.putExtra("Collection", collection);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public void deleteCollectionFromDB(String collection) {
        mDatabase.child(facebookID).child(collection).removeValue();
    }

    public void deleteItemFromDB(String collection, String item) {
        mDatabase.child(facebookID).child(collection).child(collection).child(item).removeValue();
    }
}


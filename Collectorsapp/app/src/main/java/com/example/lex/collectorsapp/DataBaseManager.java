package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by lex on 1/16/2018.
 */

class DatabaseManager {

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    // set database instance
    public void setDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void getCollectionsFromDB(final Context context, final ListView listView) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // make the Specs arraylist
                ArrayList<String> collections = new ArrayList<String>();

                // get the right data and store it in the arraylist
                for (DataSnapshot collection : dataSnapshot.child(mAuth.getUid()).getChildren()) {
                        collections.add(collection.getKey());
                }

                Collections.sort(collections, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                // inflate the eatlist in the listview
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.collectionlayout, collections);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }};
        mDatabase.addValueEventListener(postListener);
    }

    // get information form the database
    public void getItemsFromDB(final Context context, final ListView listView, final String Collection) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // make the Specs arraylist
                ArrayList<Specs> specsList = new ArrayList<Specs>();

                // get the right data and store it
                for (DataSnapshot item : dataSnapshot.child(mAuth.getUid()).child(Collection).child(Collection).getChildren()){
                        specsList.add(item.getValue(Specs.class));
                }

                // #### TODO
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

    public void addCollectionToDB(Context context, String title,
                                  ArrayList<String> specNames, ArrayList<String> specVars) {
        for (Integer i = 0; i < specNames.size(); i++) {
            mDatabase.child(mAuth.getUid()).child(title).child(title + "Specs").child(specNames.get(i)).setValue(specVars.get(i));
        }
        mDatabase.child(mAuth.getUid()).child(title).child(title).setValue(title);

        // go back to collections
        Intent intent = new Intent(context, CollectionsActivity.class);
        context.startActivity(intent);
    }

    // add values to the database
    public void addItemToDB(Context context, Specs specs, String collection) {
        mDatabase.child(mAuth.getUid()).child(collection).child(collection).child(specs.getName()).setValue(specs);

        // go back to TODO
        Intent intent = new Intent(context, CollectionViewActivity.class);
        context.startActivity(intent);
    }

    public void deleteCollectionFromDB(String collection) {
        mDatabase.child(mAuth.getUid()).child(collection).removeValue();
    }

    public void deleteItemFromDB(String collection, String item) {
        mDatabase.child(mAuth.getUid()).child(collection).child(collection).child(item).removeValue();
    }
}


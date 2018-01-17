package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    // get information form the database
    public void getFromDB(final Context context, final ListView eatList) {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // make the Specs arraylist
                ArrayList<Specs> specsList = new ArrayList<Specs>();

                // get the right data and store it in the arraylist
                for (DataSnapshot house : dataSnapshot.getChildren()) {
                    for (DataSnapshot person : house.getChildren()){
                        specsList.add(person.getValue(Specs.class));
                    }
                }

                // ####
                Collections.sort(specsList, new Comparator<Specs>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public int compare(Specs ed1, Specs ed2) {
                        return (ed1.name.compareTo(ed2.getName()));
                    }
                });

                // inflate the eatlist in the listview
                ItemViewAdapter eatListAdapter = new ItemViewAdapter(context, specsList);
                eatList.setAdapter(eatListAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }};
        mDatabase.addValueEventListener(postListener);
    }

    public void addUser(String userID) {
        mDatabase.setValue(userID);
    }

    public void addCollectionToDB(Context context, String title) {
        mDatabase.child(mAuth.getUid()).child(title).setValue(title);

        // go back to TODO
        Intent intent = new Intent(context, CollectionsActivity.class);
        context.startActivity(intent);
    }

    // add values to the database
    public void addItemToDB(Context context, Specs specs, String collection) {
        mDatabase.child(mAuth.getUid()).child(collection).child(specs.getName()).setValue(specs);

        // go back to TODO
        Intent intent = new Intent(context, ItemViewActivity.class);
        context.startActivity(intent);
    }

}

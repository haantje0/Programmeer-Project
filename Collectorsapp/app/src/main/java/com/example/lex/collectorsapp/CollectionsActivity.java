package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class CollectionsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    String[] collections = {"Postzegels", "Stripboeken", "Films"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        populateListView();
        clickcallback();
    }

    private void populateListView() {
        // build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.collectionlayout, collections);

        // configure the list view
        ListView listView = (ListView) findViewById(R.id.listViewCollections);
        listView.setAdapter(adapter);
    }

    private void clickcallback() {
        ListView listView = (ListView) findViewById(R.id.listViewCollections);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();

                Intent intent = new Intent(context, CollectionViewActivity.class);
                intent.putExtra("Collection", collections[position]);

                context.startActivity(intent);
            }
        });

    }

    public void AddCollection(View view) {
        Intent intent = new Intent(this, AddCollectionActivity.class);
        this.startActivity(intent);
    }


}

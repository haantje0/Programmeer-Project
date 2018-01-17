package com.example.lex.collectorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCollectionActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    EditText editTextTitle;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager.setDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "implement: options for extra specs.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void setTitle() {
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        title = editTextTitle.getText().toString();
    }

    public void SaveCollection(View view) {
        setTitle();

        // check if the user chose a Title
        if (title == null) {
            Toast.makeText(AddCollectionActivity.this, "Give this collection a title!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            dbManager.addCollectionToDB(AddCollectionActivity.this, title);
        }
    }
}

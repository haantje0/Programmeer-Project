package com.example.lex.collectorsapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.lex.collectorsapp.R.id.container;

public class AddCollectionActivity extends AppCompatActivity {


    DatabaseManager dbManager = new DatabaseManager();

    EditText editTextTitle;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        dbManager.setDatabase();
    }

    public void AddSpecs(View arg0) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddCollectionActivity.this);
        builder.setTitle("Extra Specification:");

        LayoutInflater inflater = AddCollectionActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.spec_dialog, null);
        builder.setView(dialogView);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // set new spec
                TextView textView = dialogView.findViewById(R.id.editTextNewSpec);
                String newSpec = textView.getText().toString();

                // put new spec in view
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutExtraSpecs);

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.spec_row, null);
                TextView textOut = (TextView)addView.findViewById(R.id.textout);
                textOut.setText(newSpec);
                TextView buttonRemove = (TextView) addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});

                linearLayout.addView(addView);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    };

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

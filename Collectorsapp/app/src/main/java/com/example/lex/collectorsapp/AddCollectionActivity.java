package com.example.lex.collectorsapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddCollectionActivity extends AppCompatActivity {


    DatabaseManager dbManager = new DatabaseManager();

    EditText editTextTitle;

    String title;

    Integer id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        dbManager.setDatabase();
    }

    public void setTitle() {
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        title = editTextTitle.getText().toString();
    }

    public void AddSpecs(View view) {
        LinearLayout linearLayout = findViewById(R.id.LinearLayoutExtraSpecs);

        //added LayoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //add textView
        TextView textView = new TextView(this);
        textView.setText("Spec Name");
        textView.setLayoutParams(params);

        // added Button
        Button button = new Button(this);
        button.setText("Delete");
        button.setTextColor(Color.RED);
        button.setGravity(Gravity.RIGHT);
        button.setLayoutParams(params);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LinearLayout LLH = findViewById(v.getId());
                LLH.setVisibility(View.GONE);
            }
        });

        LinearLayout linearLayoutH = new LinearLayout(this);
        linearLayoutH.setId(id);
        linearLayoutH.setLayoutParams(params);
        linearLayoutH.setOrientation(LinearLayout.HORIZONTAL);

        //added the textView and the Button to LinearLayout
        linearLayout.addView(linearLayoutH);

        linearLayoutH.addView(textView);
        linearLayoutH.addView(button);

        id += 1;
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

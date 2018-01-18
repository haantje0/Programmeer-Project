package com.example.lex.collectorsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ItemViewActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    Specs specs = new Specs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        specs.setName(intent.getStringExtra("Name"));
        specs.setDescription(intent.getStringExtra("Description"));
        specs.setDate(intent.getStringExtra("Date"));
        specs.setAmount(intent.getStringExtra("Amount"));
        specs.setExtraSpecs(intent.getStringExtra("ExtraSpecs"));

        makeTheView();
    }

    public void makeTheView() {
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        TextView textViewAmount = (TextView) findViewById(R.id.textViewAmount);
        TextView textViewExtraSpec = (TextView) findViewById(R.id.textViewExtraSpec);

        textViewName.setText("Name: " + specs.getName());
        textViewDescription.setText("Description: " + specs.getDescription());
        textViewDate.setText("Date: " + specs.getDate());
        textViewAmount.setText("Amount: " + specs.getAmount());
        textViewExtraSpec.setText("Extra Spec: " + specs.getExtraSpecs());
    }

}

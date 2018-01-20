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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.lex.collectorsapp.R.id.container;

public class AddCollectionActivity extends AppCompatActivity {
    
    DatabaseManager dbManager = new DatabaseManager();

    EditText editTextTitle;

    String title;
    ArrayList<String> extraSpecs = new ArrayList<String>();
    ArrayList<String> extraSpecsvars = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        dbManager.setDatabase();
    }

    public void AddSpecs(View view) {

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

                RadioGroup radioGroup = dialogView.findViewById(R.id.RadioGroup);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = radioGroup.findViewById(radioButtonID);
                String variable = " (" + radioButton.getText().toString() + ")";

                // put new spec in view
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutExtraSpecs);

                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.spec_row, null);
                TextView specName = (TextView)addView.findViewById(R.id.textout);
                specName.setText(newSpec);
                TextView specVar = (TextView)addView.findViewById(R.id.vartype);
                specVar.setText(variable);
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

    public void setExtraSpecs() {
        extraSpecs = new ArrayList<>();
        extraSpecsvars = new ArrayList<>();

        LinearLayout linearLayout = findViewById(R.id.LinearLayoutExtraSpecs);

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof View) {
                View view = (View) linearLayout.getChildAt(i);

                TextView textViewSpec = view.findViewById(R.id.textout);
                extraSpecs.add(textViewSpec.getText().toString());

                TextView textViewVar = view.findViewById(R.id.vartype);
                String var = textViewVar.getText().toString();
                extraSpecsvars.add(var.substring(2, var.length() - 1));
            }
        }
    }

    public void SaveCollection(View view) {
        setTitle();
        setExtraSpecs();

        if (title.length() == 0) {
            Toast.makeText(AddCollectionActivity.this, "Give this collection a title!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Boolean empty = false;
            for (Integer i=0; i < extraSpecs.size(); i++) {
                if (extraSpecs.get(i).length() == 0) {
                    Toast.makeText(AddCollectionActivity.this, "Remove your empty specifications!",
                            Toast.LENGTH_SHORT).show();
                    empty = true;
                }
            }
            if (empty == false) {
                dbManager.addCollectionToDB(AddCollectionActivity.this, title, extraSpecs, extraSpecsvars);
            }
        }
    }
}

package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class AddCollectionActivity extends AppCompatActivity {
    
    DatabaseManager dbManager = new DatabaseManager();

    EditText editTextTitle;
    String title;

    HashMap<String, String> extraSpecs = new HashMap<>();
    Boolean empty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

                addExtraSpecToView(newSpec, variable);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addExtraSpecToView(String newSpec, String variable) {
        LinearLayout linearLayout = findViewById(R.id.LinearLayoutExtraSpecs);
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addView = layoutInflater.inflate(R.layout.spec_row, null);

        TextView specName = addView.findViewById(R.id.textout);
        specName.setText(newSpec);

        TextView specVar = addView.findViewById(R.id.vartype);
        specVar.setText(variable);

        TextView buttonRemove = addView.findViewById(R.id.remove);
        buttonRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((LinearLayout)addView.getParent()).removeView(addView);
            }});

        linearLayout.addView(addView);
    }

    public void SaveCollection(View view) {
        setTitle();
        setExtraSpecs();

        if (title.length() == 0) {
            Toast.makeText(AddCollectionActivity.this, "Give this collection a title!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            if (!empty) {
                dbManager.addCollectionToDB(AddCollectionActivity.this, title, extraSpecs);
            }
            else {
                Toast.makeText(AddCollectionActivity.this, "Delete your empty specifications!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setTitle() {
        editTextTitle = findViewById(R.id.editTextTitle);
        title = editTextTitle.getText().toString();
    }

    public void setExtraSpecs() {
        extraSpecs = new HashMap<>();
        LinearLayout linearLayout = findViewById(R.id.LinearLayoutExtraSpecs);
        empty = false;

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) != null) {
                View view = linearLayout.getChildAt(i);

                TextView textViewSpec = view.findViewById(R.id.textout);
                TextView textViewVar = view.findViewById(R.id.vartype);

                String extraSpec = textViewSpec.getText().toString();
                String var = textViewVar.getText().toString();

                if (extraSpec.length() == 0) {
                    empty = true;
                }

                extraSpecs.put(extraSpec, var.substring(2, var.length() - 1));
            }
        }
    }
}

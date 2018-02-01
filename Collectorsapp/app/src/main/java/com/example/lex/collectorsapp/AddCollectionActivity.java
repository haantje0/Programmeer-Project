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

/**
 * Created by Lex de Haan on 1/16/2018.
 *
 * This activity lets the user add new collections. The title of the collection and extra
 * specifications can be added to the database.
 */

public class AddCollectionActivity extends AppCompatActivity {

    // make the databasemanager
    DatabaseManager dbManager = new DatabaseManager();

    // make title variables
    EditText editTextTitle;
    String title;

    // make extra specification variables
    HashMap<String, String> extraSpecs = new HashMap<>();
    Boolean empty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // set the database
        dbManager.setDatabase();
    }

    /**
     * The following functions handle the addition of extra specifications.
     * When the add button is clicked a alert dialog will popup.
     * The information from the dialog will be saved within the view.
     */

    // onclick listener for the add button
    public void AddSpecs(View view) {

        // build the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(AddCollectionActivity.this);
        builder.setTitle("Extra Specification:");

        // set the new view for the spec
        LayoutInflater inflater = AddCollectionActivity.this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.spec_dialog, null);
        builder.setView(dialogView);

        // set the response buttons from the allert dialog
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                positiveButtonListener(dialogView);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // set onclick listener for positive button
    public void positiveButtonListener(View dialogView) {
        // set name of new spec
        TextView textView = dialogView.findViewById(R.id.editTextNewSpec);
        String newSpec = textView.getText().toString();

        // set variable of new spec
        RadioGroup radioGroup = dialogView.findViewById(R.id.RadioGroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(radioButtonID);
        String variable = " (" + radioButton.getText().toString() + ")";

        addExtraSpecToView(newSpec, variable);
    }

    // put the extra specs in the view
    public void addExtraSpecToView(String newSpec, String variable) {
        // set the views
        LinearLayout linearLayout = findViewById(R.id.LinearLayoutExtraSpecs);
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.spec_row, null);

        // set textviews in the view
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

    /**
     * The following functions handle the input of the user.
     * The title and extra specs are extracted and saved in the database.
     */

    // onclick listener for the save button
    public void SaveCollection(View view) {
        setTitle();
        setExtraSpecs();

        // check if the user has given the collection and specifications a title
        if (title.length() == 0) {
            Toast.makeText(AddCollectionActivity.this, "Give this collection a title!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            if (!empty) {
                // save the new collection to the database
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

        // extract all extra specifications from the view and put them in a hashmap
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) != null) {
                View view = linearLayout.getChildAt(i);

                // set the textview
                TextView textViewSpec = view.findViewById(R.id.textout);
                TextView textViewVar = view.findViewById(R.id.vartype);

                // extract the value
                String extraSpec = textViewSpec.getText().toString();
                String var = textViewVar.getText().toString();

                // make sure the specification has a name
                if (extraSpec.length() == 0) {
                    empty = true;
                }

                // add the new specification
                extraSpecs.put(extraSpec, var.substring(2, var.length() - 1));
            }
        }
    }
}

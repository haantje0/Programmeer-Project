package com.example.lex.collectorsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class AddItemActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // make database variables
    DatabaseManager dbManager = new DatabaseManager();
    String collection;

    // make the edittexts
    EditText editTextName;
    EditText editTextDescription;

    // make image variables
    ImageView mImageView;
    String imageString;

    // make the linearlayout
    LinearLayout linearLayout;

    // make the specs
    Specs specs = new Specs();
    HashMap<String, String> extraSpecs = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // receive which collection will be viewd
        Intent intent = getIntent();
        collection = intent.getStringExtra("Collection");

        // set the used views
        mImageView = findViewById(R.id.imageViewAddPhoto);
        linearLayout = findViewById(R.id.LinearLayoutExtraTextInput);

        // set the firebase database
        dbManager.setDatabase();

        // set the extra variables and put them in the view
        dbManager.getExtraSpecsFromDB(this, linearLayout, collection);
    }

    public void AddPhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            encodeBitmap(imageBitmap);
        }
    }

    public void encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void SaveItemSpecs(View view) {
        // set given data
        setName();
        setDescription();
        setExtraSpecs();
        setImage();

        // check if the user chose an option
        if (specs.getName().length() == 0) {
            Toast.makeText(AddItemActivity.this, "Give this item a name!",
                    Toast.LENGTH_SHORT).show();
        } else {
            dbManager.addItemToDB(AddItemActivity.this, specs, collection);
        }
    }

    public void setName() {
        editTextName = findViewById(R.id.editTextName);
        String name = editTextName.getText().toString();
        specs.setName(name);
    }

    // set ######
    public void setDescription() {
        editTextDescription = findViewById(R.id.editTextDescription);
        String description = editTextDescription.getText().toString();
        specs.setDescription(description);
    }

    // set ######
    public void setExtraSpecs() {
        extraSpecs = new HashMap<>();

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) != null) {
                View view = (View) linearLayout.getChildAt(i);

                EditText editTextSpec = view.findViewById(R.id.editTextSpec);
                TextInputLayout textInputLayout = view.findViewById(R.id.textInputLayoutAddItem);

                String extraSpecName = textInputLayout.getHint().toString();
                String extraSpec = editTextSpec.getText().toString();

                extraSpecs.put(extraSpecName, extraSpec);
            }
        }
        specs.setExtraSpecs(extraSpecs);
    }

    private void setImage() {
        if (imageString == null) {
            imageString = "";
        }
        specs.setImage(imageString);
    }
}
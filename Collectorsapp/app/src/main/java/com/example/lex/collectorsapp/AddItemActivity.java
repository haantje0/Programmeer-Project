package com.example.lex.collectorsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    // set firebasemanager and databasemanager
    DatabaseManager dbManager = new DatabaseManager();

    // set the edittexts
    EditText editTextName;
    EditText editTextDescription;
    EditText editTextExtraSpecs;

    // set the imageview
    ImageView mImageView;

    LinearLayout linearLayout;

    // set specs
    Specs specs = new Specs();
    HashMap<String, String> extraSpecs = new HashMap<>();

    String collection;
    String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        collection = intent.getStringExtra("Collection");

        mImageView = (ImageView) findViewById(R.id.imageViewAddPhoto);
        linearLayout = findViewById(R.id.LinearLayoutExtraTextInput);

        // set the firebase database
        dbManager.setDatabase();

        dbManager.getExtraSpecsFromDB(this, linearLayout, collection);
    }

    public void AddPhoto(View view) {
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        imageString = imageEncoded;
    }

    // TODO set permission for camara?
    public void getImage() {
        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // set in imageview
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    // set ######
    public void setName() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        String name = editTextName.getText().toString();
        specs.setName(name);
    }

    // set ######
    public void setDescription() {
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        String description = editTextDescription.getText().toString();
        specs.setDescription(description);
    }

    // set ######
    public void setExtraSpecs() {
        extraSpecs = new HashMap<>();

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof View) {
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

    // submit the data
    // TODO zorgen dat de extra specs niet twee keer verschijnen bij verzenden
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
        }
        else {
            dbManager.addItemToDB(AddItemActivity.this, specs, collection);
        }
    }
}


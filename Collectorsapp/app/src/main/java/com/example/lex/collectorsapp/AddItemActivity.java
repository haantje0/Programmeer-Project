package com.example.lex.collectorsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // set firebasemanager and databasemanager
    DatabaseManager dbManager = new DatabaseManager();

    // set the edittexts
    EditText editTextName;
    EditText editTextDescription;
    EditText editTextDate;
    EditText editTextAmount;
    EditText editTextExtraSpecs;

    // set the imageview
    ImageView mImageView;

    // set specs
    Specs specs = new Specs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView = (ImageView) findViewById(R.id.imageViewAddPhoto);

        // set the firebase database
        dbManager.setDatabase();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
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
    public void setDate() {
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        String date = editTextDate.getText().toString();
        specs.setDate(date);
    }

    // set ######
    public void setAmount() {
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        String amount = editTextAmount.getText().toString();
        specs.setAmount(amount);
    }

    // set ######
    public void setExtraSpecs() {
        editTextExtraSpecs = (EditText) findViewById(R.id.editTextExtraSpec);
        String extraSpecs = editTextExtraSpecs.getText().toString();
        specs.setExtraSpecs(extraSpecs);
    }

    private void setImage() {
        Bitmap image =((BitmapDrawable) mImageView.getDrawable()).getBitmap();
        specs.setImage(image);
    }

    // submit the data
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void SaveItemSpecs(View view) {
        // set given data
        setName();
        setDescription();
        setDate();
        setAmount();
        setExtraSpecs();
        setImage();


        // check if the user chose an option
        if (specs.getName() == null) {
            Toast.makeText(AddItemActivity.this, "Give this item a name!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            dbManager.addToDB(AddItemActivity.this, specs);
        }
    }
}


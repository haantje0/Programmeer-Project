package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Lex de Haan on 1/16/2018.
 *
 * This activity shows the items in the clicked collection (title, description and image).
 */

public class CollectionViewActivity extends AppCompatActivity {

    // make the database
    DatabaseManager dbManager = new DatabaseManager();

    // make collection variables
    String collection;
    String friendID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_view);

        // get information of which collection you are viewing
        Intent intent = getIntent();
        friendID = intent.getStringExtra("FriendID");
        collection = intent.getStringExtra("Collection");

        // set the database
        setDatabase();

        // put the items in the listview
        ListView listView = findViewById(R.id.ListViewItems);
        dbManager.getItemsFromDB(this, listView, collection);

        // make onclick listeners for the listview
        clickcallback();
    }

    private void setDatabase() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(collection);
        setSupportActionBar(toolbar);

        // check which database has to be displayed
        if (friendID == null) {
            dbManager.setDatabase();
        }
        else {
            // hide the add button when you are viewing a friends collection
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.GONE);

            dbManager.setFriendDatabase(friendID);
        }
    }

    /**
     * The following functions create the toolbar and sets its functionality.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the right menu
        if (friendID == null) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }
        else {
            getMenuInflater().inflate(R.menu.menu_friends, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // set listeners to the different buttons
        if (id == R.id.friends) {
            Intent intent = new Intent(this, FriendsActivity.class);
            this.startActivity(intent);
            return true;
        }
        else if (id == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            return true;
        }
        else if (id == R.id.home) {
            Intent intent = new Intent(this, CollectionsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The following functions register onclick callbacks for the listview and the add button
     */

    // add onclick listeners to the listview items
    private void clickcallback() {
        final ListView listView = findViewById(R.id.ListViewItems);

        listView.setOnItemClickListener(ClickListener());

        if (friendID == null) {
            listView.setOnItemLongClickListener(LongClickListener());
        }
    }

    // set listview click listener
    public AdapterView.OnItemClickListener ClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();

                // go to the clicked item
                Intent intent = new Intent(context, ItemViewActivity.class);

                Specs specs = (Specs) parent.getItemAtPosition(position);
                intent.putExtra("Name", specs.getName());
                intent.putExtra("Description", specs.getDescription());
                intent.putExtra("Image", specs.getImage());
                intent.putExtra("ExtraSpecs", specs.getExtraSpecs());

                context.startActivity(intent);
            }
        };
    }

    // set listview longclick listener
    public AdapterView.OnItemLongClickListener LongClickListener() {
        return  new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                final Specs specs = (Specs) parent.getItemAtPosition(position);

                // make an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionViewActivity.this);
                builder.setMessage("Delete this item?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // delete the item
                        dbManager.deleteItemFromDB(collection, specs.getName());
                    }
                });
                builder.setNegativeButton("NO!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        };
    }

    // onclick listener for the add item button
    public void AddItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("Collection", collection);

        this.startActivity(intent);
    }
}
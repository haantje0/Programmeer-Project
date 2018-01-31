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
 * This activity shows the collections that a user has. This activity is the home page for a user.
 * It will show its own collections.
 *
 * When a collection is clicked, it will bring you to that collection. When a collection is
 * longclicked, it will ask the user if he wats to delete the collection or not via a alert dialog.
 *
 * There is a floating action button with a plus sign. When this is clicked, it will bring the user
 * to the AddCollectionActivity.
 *
 * When this activity is opened from the FriendsActivity, it will show the collections of the chosen
 * friend. This will also create a different toolbar and this will make the add button disappear.
 */

public class CollectionsActivity extends AppCompatActivity {

    // make the database
    DatabaseManager dbManager = new DatabaseManager();

    // make friend variables
    String friendID;
    String friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        // get information of who's collection you are viewing
        Intent intent = getIntent();
        friendID = intent.getStringExtra("FriendID");
        friendName = intent.getStringExtra("FriendName");

        // set the database
        setDatabase();

        // put the collections in the listview
        ListView listView = findViewById(R.id.listViewCollections);
        dbManager.getCollectionsFromDB(this, listView);

        // make onclick listeners for the listview
        clickcallback();
    }

    private void setDatabase() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        // check which database has to be displayed and say it to the DataBaseManager
        if (friendID == null) {
            toolbar.setTitle("Your Collections");
            setSupportActionBar(toolbar);

            dbManager.setDatabase();
        }
        else {
            toolbar.setTitle(friendName);
            setSupportActionBar(toolbar);

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
     * The following register onclick callbacks for the listview and the add button
     */

    // add onclick listeners to the listview items
    private void clickcallback() {
        final ListView listView = findViewById(R.id.listViewCollections);

        listView.setOnItemClickListener(ClickListener());
        listView.setOnItemLongClickListener(LongClickListener());
    }

    // set listview click listener
    public AdapterView.OnItemClickListener ClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();

                // go to the clicked collection
                Intent intent = new Intent(context, CollectionViewActivity.class);
                intent.putExtra("Collection", (String) parent.getItemAtPosition(position));
                intent.putExtra("FriendID", friendID);

                context.startActivity(intent);
            }
        };
    }

    // set listview longclick listener
    public AdapterView.OnItemLongClickListener LongClickListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View viewClicked, final int position, long id) {
                // make an alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionsActivity.this);
                builder.setMessage("Delete this collection?");

                // delete the collection if the user wants it
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // delete the collection
                        dbManager.deleteCollectionFromDB((String) parent.getItemAtPosition(position));
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

    // onclick listener for the add collection button
    public void AddCollection(View view) {
        Intent intent = new Intent(this, AddCollectionActivity.class);
        this.startActivity(intent);
    }


}

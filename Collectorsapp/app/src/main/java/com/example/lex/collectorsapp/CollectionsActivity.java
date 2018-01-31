package com.example.lex.collectorsapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

// used toolbar info from https://medium.com/@101/android-toolbar-for-appcompatactivity-671b1d10f354

public class CollectionsActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    String friendID;
    String friendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        Intent intent = getIntent();
        friendID = intent.getStringExtra("FriendID");
        friendName = intent.getStringExtra("FriendName");

        setDatabase();

        ListView listView = (ListView) findViewById(R.id.listViewCollections);
        dbManager.getCollectionsFromDB(this, listView);

        clickcallback();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private void setDatabase() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (friendID == null) {
            toolbar.setTitle("Your Collections");
            setSupportActionBar(toolbar);

            dbManager.setDatabase();
        }
        else {
            toolbar.setTitle(friendName);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.GONE);

            dbManager.setFriendDatabase(friendID);
        }
    }

    private void clickcallback() {
        final ListView listView = (ListView) findViewById(R.id.listViewCollections);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();

                Intent intent = new Intent(context, CollectionViewActivity.class);
                intent.putExtra("Collection", (String) parent.getItemAtPosition(position));
                intent.putExtra("FriendID", (String) friendID);

                context.startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View viewClicked, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionsActivity.this);
                builder.setMessage("Delete this collection?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        });
    }

    public void AddCollection(View view) {
        Intent intent = new Intent(this, AddCollectionActivity.class);
        this.startActivity(intent);
    }


}

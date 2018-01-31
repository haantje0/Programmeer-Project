package com.example.lex.collectorsapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CollectionViewActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    List<Specs> items = new ArrayList<Specs>();

    String collection;
    String friendID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_view);

        Intent intent = getIntent();
        friendID = intent.getStringExtra("FriendID");
        collection = intent.getStringExtra("Collection");

        setDatabase();

        ListView listView = (ListView) findViewById(R.id.ListViewItems);
        dbManager.getItemsFromDB(this, listView, collection);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
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
        toolbar.setTitle(collection);
        setSupportActionBar(toolbar);

        if (friendID == null) {
            dbManager.setDatabase();
        }
        else {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.GONE);

            dbManager.setFriendDatabase(friendID);
        }
    }

    private void clickcallback() {
        final ListView listView = (ListView) findViewById(R.id.ListViewItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();

                Intent intent = new Intent(context, ItemViewActivity.class);

                Specs specs = (Specs) parent.getItemAtPosition(position);
                intent.putExtra("Name", (String) specs.getName());
                intent.putExtra("Description", (String) specs.getDescription());
                intent.putExtra("Image", (String) specs.getImage());
                intent.putExtra("ExtraSpecs", (HashMap<String, String>) specs.getExtraSpecs());

                context.startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                final Specs specs = (Specs) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionViewActivity.this);
                builder.setMessage("Delete this item?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        });
    }

    public void AddItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("Collection", collection);

        this.startActivity(intent);
    }
}

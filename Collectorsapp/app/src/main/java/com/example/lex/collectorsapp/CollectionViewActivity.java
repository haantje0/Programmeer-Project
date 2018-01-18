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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionViewActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    List<Specs> items = new ArrayList<Specs>();

    String collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager.setDatabase();

        Intent intent = getIntent();
        collection = intent.getStringExtra("Collection");

        TextView textView = (TextView) findViewById(R.id.textViewHeading);
        textView.setText(collection);

        ListView listView = (ListView) findViewById(R.id.ListViewItems);
        dbManager.getItemsFromDB(this, listView, collection);

        clickcallback();
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
                intent.putExtra("Date", (String) specs.getDate());
                intent.putExtra("Amount", (String) specs.getAmount());
                intent.putExtra("ExtraSpecs", (String) specs.getExtraSpecs());

                context.startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                final Specs specs = (Specs) parent.getItemAtPosition(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(CollectionViewActivity.this);
                builder.setMessage("delete this item?");

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
    };

    public void AddItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("Collection", collection);

        this.startActivity(intent);
    }
}

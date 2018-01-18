package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class CollectionsActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        dbManager.setDatabase();

        ListView listView = (ListView) findViewById(R.id.listViewCollections);
        dbManager.getCollectionsFromDB(this, listView);

        clickcallback();
    }

    private void clickcallback() {
        final ListView listView = (ListView) findViewById(R.id.listViewCollections);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();

                Intent intent = new Intent(context, CollectionViewActivity.class);
                intent.putExtra("Collection", (String) parent.getItemAtPosition(position));

                context.startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, View viewClicked, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CollectionsActivity.this);
                        builder.setMessage("delete this collection?");

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


                return true;
            }
        });
    }

    public void AddCollection(View view) {
        Intent intent = new Intent(this, AddCollectionActivity.class);
        this.startActivity(intent);
    }


}

package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollectionViewActivity extends AppCompatActivity {

    List<StandardSpecs> items = new ArrayList<StandardSpecs>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String collection = intent.getStringExtra("Collection");

        TextView textView = (TextView) findViewById(R.id.textViewHeading);
        textView.setText(collection);

        populateListView();
        clickcallback();
    }

    private void populateListView() {
        // build adapter

        StandardSpecs first = new StandardSpecs("eerste item", "beschrijving");
        StandardSpecs second = new StandardSpecs("tweede item", "nog een beschrijving");
        StandardSpecs third = new StandardSpecs("derde item", "weer een beschrijving");

        items.add(first);
        items.add(second);
        items.add(third);

        Context context = getBaseContext();

        // configure the list view
        ItemViewAdapter itemViewAdapter = new ItemViewAdapter(context, items);
        ListView listView = (ListView) findViewById(R.id.ListViewItems);
        listView.setAdapter(itemViewAdapter);
    }

    private void clickcallback() {
        ListView listView = (ListView) findViewById(R.id.ListViewItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();

                Intent intent = new Intent(context, ItemViewActivity.class);
                intent.putExtra("Title", items.get(position).getTitle());
                intent.putExtra("Description", items.get(position).getDescription());

                context.startActivity(intent);
            }
        });
    }

    public void AddItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        this.startActivity(intent);
    }
}

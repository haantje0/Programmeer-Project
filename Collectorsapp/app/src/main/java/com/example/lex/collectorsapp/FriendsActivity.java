package com.example.lex.collectorsapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;

// TODO https://github.com/sallySalem/Facebook-Friends-list

public class FriendsActivity extends AppCompatActivity {

    DatabaseManager dbManager = new DatabaseManager();

    private ArrayList<FriendItem> friendsList = new ArrayList<FriendItem>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager.setDatabase();

        setFriendsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // TODO noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(this, CollectionsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            return true;
        }
        else if (id == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFriendsList() {
        //fbToken return from login with facebook
        AccessToken fbToken = AccessToken.getCurrentAccessToken();
        GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
                "/me/friends", new GraphRequest.Callback() {
                    // TODO gives no picture
                    @Override
                    public void onCompleted(GraphResponse response) {
                        parseResponse(response.getJSONObject());
                    }
                }

        ).executeAsync();
    }

    private void parseResponse(JSONObject friends ) {

        try {
            JSONArray friendsArray = (JSONArray) friends.get("data");
            if (friendsArray != null) {
                for (int i = 0; i < friendsArray.length(); i++) {
                    FriendItem item = new FriendItem();
                    try {
                        // TODO
                        item.setUserId(friendsArray.getJSONObject(i).get("id").toString());
                        item.setUserName(friendsArray.getJSONObject(i).get("name").toString());

                        JSONObject picObject = new JSONObject(friendsArray
                                .getJSONObject(i).get("picture").toString());
                        String picURL = (new JSONObject(picObject
                                .get("data").toString())).get("url").toString();
                        item.setPictureURL(picURL);
                        friendsList.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        friendsList.add(item);
                    }
                }
                // facebook use paging if have "next" this mean you still have friends if not start load fbFriends list
                String next = friends.getJSONObject("paging")
                        .getString("next");
                if (next != null) {
                    //getFBFriendsList(next);
                } else {
                    loadFriendsList();
                }
            }
        } catch (JSONException e1) {
            loadFriendsList();
            e1.printStackTrace();
        }
    }

    private void loadFriendsList() {
        listView = findViewById(R.id.ListViewFriends);
        FriendsAdapter adapter = new FriendsAdapter(this, friendsList);
        listView.setAdapter(adapter);

        clickcallback();
    }

    private void clickcallback() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();
                Intent intent = new Intent(context, CollectionsActivity.class);

                FriendItem friend = (FriendItem) parent.getItemAtPosition(position);
                intent.putExtra("FriendID", (String) friend.getUserId());
                intent.putExtra("FriendName", (String) friend.getUserName());

                context.startActivity(intent);
            }
        });
    }
}

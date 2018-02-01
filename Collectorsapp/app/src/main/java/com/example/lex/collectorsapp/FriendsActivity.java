package com.example.lex.collectorsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Lex de Haan on 1/16/2018.
 *
 * This activity displays the Facebook friends that also use the app.
 */

public class FriendsActivity extends AppCompatActivity {

    // make the database
    DatabaseManager dbManager = new DatabaseManager();

    // make friends variables
    private ArrayList<FriendItem> friendsList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set the database manager
        dbManager.setDatabase();

        // set the friends
        setFriendsList();
    }

    /**
     * The following functions create the toolbar and sets its functionality.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // set listeners to the different buttons
        if (id == R.id.logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
            return true;
        }
        else if (id == R.id.home) {
            Intent intent = new Intent(this, CollectionsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The following functions create friendlist and display it.
     */

    private void setFriendsList() {
        //fbToken return from login with facebook
        AccessToken fbToken = AccessToken.getCurrentAccessToken();
        GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
                "/me/friends", new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        parseResponse(response.getJSONObject());
                    }
                }
        ).executeAsync();
    }

    // get the correct data from the JSON object
    private void parseResponse(JSONObject friends ) {
        try {
            JSONArray friendsArray = (JSONArray) friends.get("data");
            if (friendsArray != null) {
                for (int i = 0; i < friendsArray.length(); i++) {
                    FriendItem item = new FriendItem();
                    try {
                        // get the name and id
                        String userID = friendsArray.getJSONObject(i).get("id").toString();
                        item.setUserId(userID);
                        item.setUserName(friendsArray.getJSONObject(i).get("name").toString());

                        // get the photo
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
                // Facebook use paging if have "next" this mean you still have friends
                // if not start load fbFriends list
                String next = friends.getJSONObject("paging")
                        .getString("next");
                if (next == null) {
                    loadFriendsList();
                }
            }
        } catch (JSONException e1) {
            loadFriendsList();
            e1.printStackTrace();
        }
    }

    // put the friends list in a view
    private void loadFriendsList() {
        listView = findViewById(R.id.ListViewFriends);
        FriendsAdapter adapter = new FriendsAdapter(this, friendsList);
        listView.setAdapter(adapter);

        clickcallback();
    }

    // set onclick listener for the friends listview
    private void clickcallback() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Context context = getBaseContext();
                Intent intent = new Intent(context, CollectionsActivity.class);

                FriendItem friend = (FriendItem) parent.getItemAtPosition(position);
                intent.putExtra("FriendID", friend.getUserId());
                intent.putExtra("FriendName", friend.getUserName());

                context.startActivity(intent);
            }
        });
    }
}

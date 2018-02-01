package com.example.lex.collectorsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Lex de Haan on 1/16/2018.
 *
 * This Activity is the MainActivity. The user can log in via Facebook and go to his collections.
 */

public class LoginActivity extends AppCompatActivity {

    static final String TAG = "loginactivity";

    // create a callback manager
    CallbackManager callbackManager = CallbackManager.Factory.create();

    // make the Firebase authorisation
    private FirebaseAuth mAuth;

    // make some views
    Button continueAsButton;
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set the views
        continueAsButton = findViewById(R.id.ContinueAsButton);
        welcomeTextView = findViewById(R.id.TextViewWelcome);

        // set Firebase authorisation
        mAuth = FirebaseAuth.getInstance();

        // initialize Facebook button
        InitializeFacebookButton();

        // set the continue button
        setButton();
    }

    /**
     * The following functions set the Facebook button and they also handle all the interaction with
     * the Facebook API.
     *
     * When you are logged in via Facebook, it also sets the Firebase authorisation.
     */

    // initialize Facebook Login button
    public void InitializeFacebookButton() {
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            // register the result
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "loginresult cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.toString());
            }
        });

        // track the accestoken to see when someone logged out
        setAccesTokenTracker();
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // when sign in is succesfull, go to the next activity
                            Intent intent = new Intent(LoginActivity.this, CollectionsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            LoginActivity.this.startActivity(intent);
                        } else {
                            // if sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "handling access token failed" + e.toString());
                    }
                });
    }

    public void setAccesTokenTracker() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    // change the view wen you are logged in or out
                    setButton();
                }
            }
        };
    }

    // when Facebook returns its result it is handled here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * The following functions set the continue button
     */

    // set visibility of the continue button and the welcome text
    private void setButton() {
        // check if there is someone logged in
        if (AccessToken.getCurrentAccessToken() == null) {
            LoginManager.getInstance().logOut();
            mAuth.signOut();
            continueAsButton.setVisibility(View.GONE);
            welcomeTextView.setVisibility(View.GONE);
        }
        else {
            String name = mAuth.getCurrentUser().getDisplayName().toString();
            welcomeTextView.setText("Welcome " + name + "!");
        }
    }

    // onclick listener for the continue button
    public void ContinueAs(View view) {
        Intent intent = new Intent(this, CollectionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }
}
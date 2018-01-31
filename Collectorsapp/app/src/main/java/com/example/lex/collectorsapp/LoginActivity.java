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
 * Created by lex on 1/16/2018.
 */

public class LoginActivity extends AppCompatActivity {

    static final String TAG = "loginactivity";

    CallbackManager callbackManager = CallbackManager.Factory.create();

    private FirebaseAuth mAuth;

    Button continueAsButton;
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        continueAsButton = findViewById(R.id.ContinueAsButton);
        welcomeTextView = findViewById(R.id.TextViewWelcome);

        mAuth = FirebaseAuth.getInstance();

        InitializeFacebookManager();
        InitializeFacebookButton();

        setButton();
    }

    public void ContinueAs(View view) {
        Intent intent = new Intent(this, CollectionsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
    }

    public void InitializeFacebookButton() {
        // Initialize Facebook Login button
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

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

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    setButton();
                }
            }
        };
    }

    public void InitializeFacebookManager() {
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        final AccessToken accessToken = loginResult.getAccessToken();
                        handleFacebookAccessToken(accessToken);
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(TAG, exception.toString());
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, CollectionsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            LoginActivity.this.startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "handling access token failed" + e.toString());
                    }
                });
    }

    private void setButton() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
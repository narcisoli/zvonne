package com.example.narcis.zvonne;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;

    CardView imagefacebook;

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;

    private int x;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent a = new Intent(this, MainActivity.class);
            startActivity(a);
            finish();
            Log.i("Debug","S-a conectat automat");
        }
        imagefacebook=(CardView) findViewById(R.id.iamgefacebook);

        progressBar=(ProgressBar)findViewById(R.id.progressBar2);

        imagefacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.callOnClick();
                progressBar.setVisibility(View.VISIBLE);
                Log.i("Debug","S-a apasat pe Facebook");
            }
        });
        loginButton = (LoginButton) findViewById(R.id.facebooklogin);
        loginButton.setReadPermissions("email");
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.i("Debug","cancel facebook");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i("Debug","error facebook");
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
                            Intent a = new Intent(login.this, MainActivity.class);
                            startActivity(a);
                            finish();



                        } else {

                        }


                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //s-o conectat cu fb da cu google nu mere  si traba sa facem logout ... ca intra in aplicatie se conecteaza cu fb si dupa ce apasa logout nu iese esti aici ma???? coie

}

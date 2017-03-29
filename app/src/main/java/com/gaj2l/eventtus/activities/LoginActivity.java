package com.gaj2l.eventtus.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gaj2l.eventtus.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

/**
 * Created by lucas tomasi on 28/03/17.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private static final int RC_SIGN_IN = 9001;
    private SignInButton    signInButton;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private LoginButton     btnFacebook;
    private Button          btnFace;
    private Button          btnGoogle;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Login Google
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build() ).build();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //

        //Login Facebook
        btnFacebook     = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        btnFacebook.registerCallback( callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,picture");

                GraphRequest request = GraphRequest.newMeRequest( loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        try
                        {
                            // save/verify user
                            redirect();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {}
        });
        //

        // Botões
        btnFacebook.setVisibility(View.INVISIBLE);
        btnFace = (Button) findViewById(R.id.btnFace);
        btnFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFacebook.callOnClick();
            }
        });
        signInButton.setVisibility(View.INVISIBLE);
        btnGoogle = (Button) findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //

        if( isLogged() ) {
            if( isLogout() ) {
                logout();
            } else {
                redirect();
            }
        }
    }

    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            // save/verify user
            GoogleSignInAccount acct = result.getSignInAccount();
            redirect();
        }
    }

    private boolean isLogout()
    {
        Bundle opt = getIntent().getExtras();
        return  ( opt != null && opt.getBoolean("logout") );
    }

    private boolean isLogged()
    {
        return  ( Profile.getCurrentProfile() != null || mGoogleApiClient.isConnected() );
    }

    private void redirect()
    {
        Intent event = new Intent(LoginActivity.this, EventActivity.class);
        startActivity( event );
    }

    public void logout()
    {
        // Logout Google
        if( mGoogleApiClient.isConnected() )
        {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient.disconnect();
        }

        // Logout Facebook
        if( Profile.getCurrentProfile() != null  )
        {
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Login Google
        if (requestCode == RC_SIGN_IN)
        {
            handleSignInResult( Auth.GoogleSignInApi.getSignInResultFromIntent(data) );
        }
        //

        // Login Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}
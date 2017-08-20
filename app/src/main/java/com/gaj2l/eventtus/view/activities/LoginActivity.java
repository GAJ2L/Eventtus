package com.gaj2l.eventtus.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
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
import com.gaj2l.eventtus.ioc.ComponentProvider;
import com.gaj2l.eventtus.lib.Internet;
import com.gaj2l.eventtus.lib.Message;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;
import com.gaj2l.eventtus.models.User;
import com.gaj2l.eventtus.services.web.TokenWebService;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

/**
 * Created by Lucas Tomasi on 28/03/17.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback
{
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private LoginButton btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button btnFace = (Button) findViewById(R.id.btnFace);
        Button btnGoogle = (Button) findViewById(R.id.btnGoogle);

        //Login Google
        mGoogleApiClient = new GoogleApiClient.Builder(this).addOnConnectionFailedListener(this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        //Login Facebook
        btnFacebook = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        btnFacebook.registerCallback(callbackManager, this);
        //

        // Botões
        btnFacebook.setVisibility(View.INVISIBLE);
        btnFacebook.setReadPermissions("public_profile", "email");

        btnFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                btnFacebook.callOnClick();
            }
        });

        signInButton.setVisibility(View.INVISIBLE);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                signIn();
            }
        });

        if (getIntent().getBooleanExtra("logout", false))
        {
            logout();
        }
        else
        {
            redirectIfUserLogged();
        }
    }

    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void redirectIfUserLogged()
    {
        long user_id = Session.getInstance(getApplicationContext()).getLong("user");

        if (user_id != 0)
        {
            User user = ComponentProvider.getServiceComponent().getUserService().get(user_id);
            if (user != null)
            {
                this.redirect(user);
            }
            else
            {
                Session.getInstance(getApplicationContext()).put("user", 0);
                Session.getInstance(getApplicationContext()).put("username", "");
                Session.getInstance(getApplicationContext()).put("email", "");
                Session.getInstance(getApplicationContext()).put("image", "");
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) throws Exception
    {
        if (result.isSuccess())
        {
            User user = getUserByGoogle(result.getSignInAccount());
            onSaveUser(user);
            redirect(user);
        }
        else
        {
            throw new Exception(getResources().getString(R.string.err_btn_google));
        }
    }

    private User getUserByFacebook(JSONObject object)
    {
        User user = new User();

        try
        {
            user.setName(object.getString("name"));
            user.setMail(object.getString("email"));
            user.setMethodAutentication(User.METHOD_FACEBOOK);
            if (object.has("picture"))
                user.setImage( Util.bitmap2base64(Util.getBitmap(object.getJSONObject("picture").getJSONObject("data").getString("url"))) );
        }
        catch (Exception e)
        {
            Message.show(getApplicationContext(),R.string.err_btn_facebook);
        }

        return user;
    }

    private User getUserByGoogle(GoogleSignInAccount acct) throws Exception
    {
        User user = new User();
        user.setName(acct.getDisplayName());
        user.setMail(acct.getEmail());
        user.setMethodAutentication(User.METHOD_GOOGLE);
        if (acct.getPhotoUrl() != null)
            user.setImage( Util.bitmap2base64(Util.getBitmap(acct.getPhotoUrl().toString())) );

        return user;
    }

    private void onSaveUser(User user) throws Exception
    {
        ComponentProvider.getServiceComponent().getUserService().create(user);
    }

    private void redirect(User user)
    {
        Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
        Session.getInstance(getApplicationContext()).put("user", user.getId());
        Session.getInstance(getApplicationContext()).put("username", user.getName());
        Session.getInstance(getApplicationContext()).put("email", user.getMail());
        Session.getInstance(getApplicationContext()).put("image", user.getImage());

        if(Internet.isConnect(getApplicationContext()))
        {
            TokenWebService.save(user.getMail());
        }
        finish();
        startActivity(intent);
    }

    public void logout()
    {
        // Logout Google
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
        {
            @Override
            public void onConnected(@Nullable Bundle bundle)
            {
                if (mGoogleApiClient.isConnected())
                {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>()
                    {
                        @Override
                        public void onResult(@NonNull Status status) {}
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {}
        });

        // Logout Facebook
        if (Profile.getCurrentProfile() != null)
        {
            LoginManager.getInstance().logOut();
        }

        Session.getInstance(getApplicationContext()).clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // Login Google
        if (requestCode == RC_SIGN_IN)
        {
            try
            {
                handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
            }
            catch (Exception e)
            {
                Message.show(getApplicationContext(), R.string.err_btn_google);
            }
        }

        // Login Google
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Message.show(getApplicationContext(), R.string.err_btn_google);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSuccess(LoginResult loginResult)
    {
        onLoginFacebook(loginResult.getAccessToken());
    }

    private void onLoginFacebook(AccessToken accessToken)
    {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture");
        GraphRequest request = GraphRequest.newMeRequest(accessToken, this);
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onCancel()
    {
        Message.show(getApplicationContext(), R.string.err_btn_facebook);
    }

    @Override
    public void onError(FacebookException error)
    {
        Message.show(getApplicationContext(), R.string.err_btn_facebook);
    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response)
    {
        try
        {
            User user = getUserByFacebook(object);
            onSaveUser(user);
            redirect(user);
        }
        catch (Exception e)
        {
            Message.show(getApplicationContext(), R.string.err_btn_facebook);
        }
    }
}
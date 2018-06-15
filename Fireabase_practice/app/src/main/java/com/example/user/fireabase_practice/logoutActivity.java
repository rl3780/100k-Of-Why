package com.example.user.fireabase_practice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;


public class logoutActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LogoutActivity";
    private TextView username;
    private Button logoutButton;
    private Button chatButton;
    private ProgressDialog pd;
    private FirebaseAuth fa;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        fa = FirebaseAuth.getInstance();

        username = (TextView) findViewById(R.id.usernameTextView);
        chatButton = (Button) findViewById(R.id.btnchat);
        logoutButton = (Button) findViewById(R.id.btnlogout);
        while (fa.getCurrentUser().getDisplayName() == null) {
        }
        username.setText(fa.getCurrentUser().getDisplayName());

        chatButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        // 登出Google用
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void onClick(View v) {
        if (v == chatButton) {
            startActivity(new Intent(this, profileActivity.class));
        }
        if (v == logoutButton) {
            // FireBase登出
            fa.signOut();

            // Google 帳號登出
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            // NOP for now
                        }
                    });

            finish();
        }
    }

    @Override
    public void onBackPressed() {

        //返回件顯示離開訊息
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //關掉整個APP
                        finishAffinity();
                    }
                })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
        Toast.makeText(logoutActivity.this, "Google Play Services error.", Toast.LENGTH_LONG).show();
    }
}

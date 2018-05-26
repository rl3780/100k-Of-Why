package com.example.user.fireabase_practice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;


public class logoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView username;
    private Button logoutButton;
    private Button chatButton;
    private ProgressDialog pd;
    private FirebaseAuth fa;

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
    }

    public void onClick(View v) {
        if (v == chatButton) {
            startActivity(new Intent(this, profileActivity.class));
        }
        if (v == logoutButton) {
            finish();
            startActivity(new Intent(this, loginActivity.class));
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
}

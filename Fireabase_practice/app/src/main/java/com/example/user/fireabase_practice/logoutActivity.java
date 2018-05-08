package com.example.user.fireabase_practice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        /*if(fa.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), profileActivity.class));
        }*/
        pd = new ProgressDialog(this);

        username = (TextView)findViewById(R.id.usernameTextView);
        chatButton = (Button)findViewById(R.id.btnchat);
        logoutButton = (Button)findViewById(R.id.btnlogout);

        chatButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v == chatButton){
            startActivity(new Intent(this, profileActivity.class));
        }
        if(v == logoutButton){
            startActivity(new Intent(this, loginActivity.class));
        }
    }
}

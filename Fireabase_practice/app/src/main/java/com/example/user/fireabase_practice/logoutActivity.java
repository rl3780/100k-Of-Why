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
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

        pd = new ProgressDialog(this);

        username = (TextView)findViewById(R.id.usernameTextView);
        chatButton = (Button)findViewById(R.id.btnchat);
        logoutButton = (Button)findViewById(R.id.btnlogout);
        while(fa.getCurrentUser().getDisplayName() == null){ }
        username.setText(fa.getCurrentUser().getDisplayName());

        chatButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v == chatButton){
            startActivity(new Intent(this, profileActivity.class));
        }
        if(v == logoutButton){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, loginActivity.class));
        }
    }
}

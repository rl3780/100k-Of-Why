package com.example.user.fireabase_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth fa;
    private TextView userEmailTextView;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fa = FirebaseAuth.getInstance();

        if(fa.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }

        FirebaseUser user = fa.getCurrentUser();

        userEmailTextView = (TextView)findViewById(R.id.userEmailTextView);
        userEmailTextView.setText("Welcome " + user.getEmail());

        logoutButton = (Button)findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == logoutButton){
            fa.signOut();
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }
    }
}

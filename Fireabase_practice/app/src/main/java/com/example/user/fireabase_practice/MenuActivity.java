package com.example.user.fireabase_practice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity/* implements View.OnClickListener*/{


    private Button chat;
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        chat = (Button)findViewById(R.id.chat_btn);
        logout = (Button)findViewById(R.id.logout_btn);
        chat.setOnClickListener(chat_btn);
        logout.setOnClickListener(logout_btn);
    }


    private final View.OnClickListener chat_btn = new View.OnClickListener() {
        @Override
        public void onClick(View btn) {
            finish();
            startActivity(new Intent(getApplicationContext(), profileActivity.class));
        }
    };
    private final View.OnClickListener logout_btn = new View.OnClickListener() {
        @Override
        public void onClick(View btn) {
            finish();
            startActivity(new Intent(getApplicationContext(), loginActivity.class));
        }
    };
}

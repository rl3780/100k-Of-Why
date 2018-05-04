package com.example.user.fireabase_practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity /*implements View.OnClickListener*/ {

    /*private FirebaseAuth fa;
    private TextView userEmailTextView;
    private Button logoutButton;*/

    private Button send_button;
    private ListView listview;
    private ArrayAdapter adapter;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*fa = FirebaseAuth.getInstance();

        if(fa.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, loginActivity.class));
        }

        FirebaseUser user = fa.getCurrentUser();

        userEmailTextView = (TextView)findViewById(R.id.userEmailTextView);
        userEmailTextView.setText("Welcome " + user.getEmail());

        logoutButton = (Button)findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(this);*/
        send_button = (Button)findViewById(R.id.button);
        text = (EditText)findViewById(R.id.editText);
        listview = (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        send_button.setOnClickListener(send_btn);
    }

    private final View.OnClickListener send_btn = new View.OnClickListener() {
        @Override
        public void onClick(View btn) {
            if(text.getText().toString().length()!=0) {
                adapter.add(text.getText().toString());
                listview.setAdapter(adapter);
                text.getText().clear();
                listview.setSelection(adapter.getCount() - 1);
            }
        }
    };
}

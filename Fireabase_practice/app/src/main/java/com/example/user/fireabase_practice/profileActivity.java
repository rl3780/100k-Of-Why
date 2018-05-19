package com.example.user.fireabase_practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileActivity extends AppCompatActivity implements View.OnClickListener /*implements View.OnClickListener*/ {

    private FirebaseAuth fa;
    /*
    private TextView userEmailTextView;
    private Button logoutButton;*/

    private Button send_button;
    private ListView listview;
    private FirebaseListAdapter<ChatMessage> adapter;
    private EditText text;
    private DatabaseReference ChatRef;
    private Button btnreturn;

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
        ChatRef = FirebaseDatabase.getInstance().getReference();
        /*
        userEmailTextView = (TextView)findViewById(R.id.userEmailTextView);
        userEmailTextView.setText("Welcome " + user.getEmail());

        logoutButton = (Button)findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(this);*/
        btnreturn = (Button)findViewById(R.id.btn_return);
        send_button = (Button)findViewById(R.id.btn_send);
        text = (EditText)findViewById(R.id.enter_message);

        listview = (ListView) findViewById(R.id.content_list);
        displayChatMessages();

        send_button.setOnClickListener(this);
        btnreturn.setOnClickListener(this);
    }

    private void displayChatMessages(){
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setLifecycleOwner(this)
                .setLayout(R.layout.message)
                .setQuery(ChatRef.child("Message"), ChatMessage.class)
                .build();

        adapter = new FirebaseListAdapter<ChatMessage>(options){
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        listview.setAdapter(adapter);
    }

    public void onClick(View v) {
        if(v == btnreturn){
            startActivity(new Intent(profileActivity.this, logoutActivity.class));
        }
        if(v == send_button){
            if(text.getText().toString().length()!=0) {
                ChatRef.child("Message")
                        .push()
                        .setValue(new ChatMessage(text.getText().toString(),
                                fa.getCurrentUser().getDisplayName())
                        );

                // Clear the input
                text.setText("");
            }
        }
    }
}

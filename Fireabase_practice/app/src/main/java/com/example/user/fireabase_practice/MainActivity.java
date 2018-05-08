package com.example.user.fireabase_practice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private TextView signupTextView;
    private ProgressDialog pd;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fa = FirebaseAuth.getInstance();

        /*if(fa.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), logoutActivity.class));
        }*/

        pd = new ProgressDialog(this);

        usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        registerButton = (Button)findViewById(R.id.registerButton);
        signupTextView = (TextView)findViewById(R.id.signupTextView);

        registerButton.setOnClickListener(this);
        signupTextView.setOnClickListener(this);
    }

    private void registerUser(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        pd.setMessage("Registering User...");
        pd.show();

        fa.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), logoutActivity.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Could not register..., please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == registerButton){
            registerUser();
        }
        if(v == signupTextView){
            startActivity(new Intent(this, loginActivity.class));
        }
    }
}

package com.example.user.fireabase_practice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        pd = new ProgressDialog(this);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        registerButton = (Button) findViewById(R.id.loginButton);
        signupTextView = (TextView) findViewById(R.id.signupTextView);


        registerButton.setOnClickListener(this);
        signupTextView.setOnClickListener(this);
    }

    private boolean email_valid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean password_valid(String password)
    {
        if(password.length() < 6)
            return false;
        else
            return true;
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, getString(R.string.enter_username) , Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,  getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.enter_password) , Toast.LENGTH_SHORT).show();
            return;
        }
        if(email_valid(email) == false)
        {
            Toast.makeText(this, getString(R.string.email_incorrect), Toast.LENGTH_SHORT).show();
            return;
        }
        if(password_valid(password) == false)
        {
            Toast.makeText(this, getString(R.string.password_incorrect), Toast.LENGTH_SHORT).show();
            return;
        }


        pd.setMessage( getString(R.string.registering) );
        pd.show();

        fa = FirebaseAuth.getInstance();

        if(fa == null)
        {
            Toast.makeText(MainActivity.this, getString(R.string.cant_instance), Toast.LENGTH_SHORT).show();
            return;
        }

        fa.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser user = fa.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(usernameEditText.getText().toString()).build();
                            user.updateProfile(profileUpdates);
                            finish();
                            startActivity(new Intent(getApplicationContext(), logoutActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.email_exist), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == registerButton) {
            registerUser();
        }
        if (v == signupTextView) {
            finish();
        }
    }
}

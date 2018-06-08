package com.example.user.fireabase_practice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class loginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView loginTextView;
    private ProgressDialog pd;
    private FirebaseAuth fa;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton sign_in_button;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";
    private Button forgetPWButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pd = new ProgressDialog(this);

        fa = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        sign_in_button = (SignInButton) findViewById(R.id.sign_in_button);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        forgetPWButton = findViewById(R.id.btnForgetPW);

        loginButton.setOnClickListener(this);
        loginTextView.setOnClickListener(this);
        sign_in_button.setOnClickListener(this);
        forgetPWButton.setOnClickListener(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }
                else{
                    Log.d(TAG, "onAuthStateChanged: signed_out");
                }
                updateUI(user);
            }
        };
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.enter_your_name, Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.enter_your_password, Toast.LENGTH_LONG).show();
            return;
        }

        pd.setMessage("Login...");
        pd.show();

        fa.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                pd.dismiss();
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmailAndPassword success");
                                }
                                else {
                                    Toast.makeText(loginActivity.this, "login error", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            loginUser();
        }
        if (v == loginTextView) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if (v == sign_in_button) {
            signIn();
        }
        if (v == forgetPWButton){
            startActivity(new Intent(this, ResetPWActivity.class));
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        fa.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();;
        if(mAuthListener != null){
            fa.removeAuthStateListener(mAuthListener);
        }
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), logoutActivity.class));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else{
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fa.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithCredential: onComplete: " + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Log.v(TAG, "signInWithCreadential", task.getException());
                                    Toast.makeText(loginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: " + connectionResult);
        Toast.makeText(loginActivity.this, "Google Play Services error.", Toast.LENGTH_LONG).show();
    }
}

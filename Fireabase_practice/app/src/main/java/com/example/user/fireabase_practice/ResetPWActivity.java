package com.example.user.fireabase_practice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPWActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSendReset;
    private Button btnBack;
    private EditText edtEmail;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        btnSendReset = findViewById(R.id.btnResetPW);
        btnBack = findViewById(R.id.btnBack);
        edtEmail = findViewById(R.id.edtResetEmail);

        btnSendReset.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        fa = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSendReset) {
            sendResetEmail();
        }
        if (v == btnBack) {
            finish();
        }
    }

    private void sendResetEmail() {
        String email = edtEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter your email!",Toast.LENGTH_SHORT).show();
        }
        else{
            fa.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ResetPWActivity.this,"Check email to reset your password!",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ResetPWActivity.this,"Fail to send reset password email!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}

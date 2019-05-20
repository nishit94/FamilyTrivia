package com.example.familyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, password;
    private TextView forgotPassword;
    private ProgressDialog progressDialog;
    private Button login, signup;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forget_password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();


    }

    private void signIn() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Signing In...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        String mail = username.getText().toString();
        String pass = password.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, Home.class));
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void validateUser() {
        String mail = username.getText().toString();
        String pass = password.getText().toString();
        if (TextUtils.isEmpty(mail)) {
            username.setError("Required");
        } else if (TextUtils.isEmpty(pass)) {
            password.setError("Required");
        } else {
            signIn();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            validateUser();
        } else if (v == signup) {
            startActivity(new Intent(MainActivity.this, Register.class));
        } else if (v == forgotPassword) {
            startActivity(new Intent(MainActivity.this, forgotpassword.class));
        }
    }
}

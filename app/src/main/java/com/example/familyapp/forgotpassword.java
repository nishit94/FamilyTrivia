package com.example.familyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpassword extends AppCompatActivity implements View.OnClickListener {

    private EditText forgot_password;
    private Button reset_button;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        forgot_password = (EditText) findViewById(R.id.forgot);
        reset_button = (Button) findViewById(R.id.reset);

        reset_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String mail = forgot_password.getText().toString();
        if (TextUtils.isEmpty(mail)) {
            forgot_password.setError("Required");
        } else {

            firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        pDialog = new ProgressDialog(forgotpassword.this);
                        pDialog.setMessage("Sending Link...");
                        pDialog.show();
                        Toast.makeText(forgotpassword.this, "Reset password link sent", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(forgotpassword.this, MainActivity.class));
                        pDialog.dismiss();
                    } else {
                        Toast.makeText(forgotpassword.this, "Failedto send link", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

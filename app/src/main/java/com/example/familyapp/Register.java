package com.example.familyapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText firstName, lastName, email, password, confirmPassword;
    private EditText birthday;
    private ProgressDialog progressDialog;
    private Button registerUser;
    private Calendar calendar = Calendar.getInstance();

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.first);
        lastName = findViewById(R.id.last);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPass);
        birthday = findViewById(R.id.birthdate);
        registerUser = findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();
        birthday.setOnClickListener(this);
        registerUser.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void validateUserDetails() {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String confirm = confirmPassword.getText().toString();
        String date = birthday.getText().toString();

        if (TextUtils.isEmpty(mail)) {
            email.setError("Enter Email");
        } else if (TextUtils.isEmpty(pass)) {
            password.setError("Enter Password");
        } else if (TextUtils.isEmpty(confirm)) {
            confirmPassword.setError("Confirm Password");

        } else if (pass.equals(confirm)) {
            registerUser();
        }
    }

    public void registerUser() {
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Signing Up...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String mail = email.getText().toString();
        String pass = password.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Registered failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == birthday) {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    birthday.setText(new StringBuilder().append(dayOfMonth).append("/")
                            .append(month).append("/").append(year));

                }
            };
            new DatePickerDialog(Register.this, dateSetListener, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();

        }
        if (v == registerUser) {
            validateUserDetails();
            Intent intent=new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

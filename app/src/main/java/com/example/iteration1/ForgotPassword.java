package com.example.iteration1;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.dataBase_CRUD.fireBase_CRUD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ActionCodeSettings;
import java.security.SecureRandom;

import java.util.ArrayList;

public class ForgotPassword extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSendCode;
    private TextView tvErrorMessage, tvLogin;

    String DBURL = "https://quickcash3130-4607d-default-rtdb.firebaseio.com/";
    fireBase_CRUD crud;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        etEmail = findViewById(R.id.etEmail);
        btnSendCode = findViewById(R.id.btnSendCode);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        tvLogin = findViewById(R.id.tvLogin);
        //create crud object
        crud = new fireBase_CRUD(FirebaseDatabase.getInstance(DBURL));

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if (!checkIfEmailExists(email)) {
                    tvErrorMessage.setText("Please enter a valid email address.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }

                if (email.isEmpty()) {
                    tvErrorMessage.setText("Please enter your email address.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                } else {
                    // Get an instance of FirebaseAuth
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    // Send a password reset email using Firebase Authentication
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPassword.this, "Reset code sent! Check your email.", Toast.LENGTH_SHORT).show();
                                        tvErrorMessage.setVisibility(View.GONE);


                                        Intent intent = new Intent(ForgotPassword.this, Login.class);
                                        intent.putExtra("email", email);
                                        startActivity(intent);
                                    } else {
                                        Exception exception = task.getException();
                                        String errorMessage = "Failed to send reset email. Please try again.";

                                        if (exception != null) {
                                            errorMessage = exception.getMessage();
                                        }

                                        tvErrorMessage.setText("Failed to send reset email. Please try again.");
                                        tvErrorMessage.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }


    // Simulated email checking function
    private boolean checkIfEmailExists(String email) {

        ArrayList<String> emailList = crud.getEmailList();
        return emailList.contains(email);
    }
}

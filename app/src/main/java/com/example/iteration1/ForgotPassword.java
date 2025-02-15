package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.dataBase_CRUD.fireBase_CRUD;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

                if (email.isEmpty()) {
                    tvErrorMessage.setText("Please enter your email address.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tvErrorMessage.setText("Please enter a valid email address.");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                } else {
                    // Simulate checking if the email exists in the database
                    boolean isEmailRegistered = checkIfEmailExists(email);
                    if (isEmailRegistered) {
                        Toast.makeText(ForgotPassword.this, "Reset code sent!", Toast.LENGTH_SHORT).show();
                        tvErrorMessage.setVisibility(View.GONE);
                    } else {
                        tvErrorMessage.setText("This email address is not registered, please register an account.");
                        tvErrorMessage.setVisibility(View.VISIBLE);
                    }
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
        //get the list of existing emails from crud
        ArrayList<String> emailList = crud.getEmailList();
        return emailList.contains(email);
    }
}

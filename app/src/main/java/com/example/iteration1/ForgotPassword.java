package com.example.iteration1;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSendCode;
    private TextView tvErrorMessage, tvLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        etEmail = findViewById(R.id.etEmail);
        btnSendCode = findViewById(R.id.btnSendCode);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        tvLogin = findViewById(R.id.tvLogin);

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
        // TODO: Implement actual email verification with backend
        return email.equals("test@example.com"); // Dummy check
    }
}

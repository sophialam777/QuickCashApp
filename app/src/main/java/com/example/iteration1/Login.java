package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText email;
    private EditText password;
    private Spinner roleSpinner;
    private String role;
    private String[] roleOptions = {"Select a role", "Employee", "Employer"};
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeLoginElements();
        initializeOnClickListener();
    }

    private void initializeLoginElements(){
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        roleSpinner = findViewById(R.id.login_role);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roleOptions);
        roleSpinner.setAdapter(arrayAdapter);
        roleSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this);
    }

    private void initializeOnClickListener() {
        login = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_link); // The Register button in your layout
        TextView forgotPasswordText = findViewById(R.id.forgotPassword); // The Forgot Password text in your layout

        // Register Button click listener
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
        });

        // Forgot Password click listener
        forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, ForgotPassword.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        role = roleOptions[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference dbref;
    String DBURL = "https://quickcash3130-4607d-default-rtdb.firebaseio.com/";

    private EditText email, password;
    private Spinner roleSpinner;
    private String[] roleOptions = {"Select a role", "Employee", "Employer"};
    private Button login;
    private LinearLayout errorLayout;
    private TextView errorMessage;

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

    private void initializeLoginElements() {
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        roleSpinner = findViewById(R.id.login_role);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roleOptions);
        roleSpinner.setAdapter(arrayAdapter);
        errorLayout = findViewById(R.id.error_layout);
        errorMessage = findViewById(R.id.error_message);
    }

    private void initializeOnClickListener() {
        login = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_link);
        TextView forgotPasswordText = findViewById(R.id.forgotPassword);

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
        });

        forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, ForgotPassword.class);
            startActivity(intent);
        });

        login.setOnClickListener(v -> validateLogin());
    }

    private void validateLogin() {
        String loginEmail = email.getText().toString().trim().toLowerCase();
        String loginPassword = password.getText().toString().trim();
        String loginRole = roleSpinner.getSelectedItem().toString();

        errorLayout.setVisibility(View.GONE);
        connectDB();

        // Basic input validation
        if (TextUtils.isEmpty(loginEmail) || TextUtils.isEmpty(loginPassword)) {
            showError("Please fill out all required fields.");
            return;
        }

        if (loginRole.equals("Select a role")) {
            showError("A user role must be selected.");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
            showError("Please enter a valid email address.");
            return;
        }

        // Check password validation
        if (!loginPassword.matches(".*[a-z].*")) {
            showError("Your password must have at least one lowercase character.");
            return;
        }
        if (!loginPassword.matches(".*[A-Z].*")) {
            showError("Your password must include at least one uppercase character.");
            return;
        }
        if (!loginPassword.matches(".*[0-9].*")) {
            showError("Your password must include at least one numeric digit.");
            return;
        }
        if (!loginPassword.matches(".*[!@#$%&].*")) {
            showError("Your password must have at least one special character: !, @, #, $, %, or &.");
            return;
        }
        if (loginPassword.length() < 8) {
            showError("Your password must be at least 8 characters long.");
            return;
        }

        // Query Firebase Database to check if the email exists
        dbref.orderByChild("email").equalTo(loginEmail).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        userAccount user = userSnapshot.getValue(userAccount.class);

                        if (user != null) {
                            // Check if password matches
                            if (!user.getPassword().equals(loginPassword)) {
                                showError("The password you entered is incorrect.");
                                return;
                            }

                            // Check if role matches
                            if (!user.getRole().equals(loginRole)) {
                                showError("The role you selected does not match your account.");
                                return;
                            }

                            // Login successful
                            Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, Registration.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    showError("No account is associated with the provided email.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showError("An error occurred while accessing the database: " + error.getMessage());
            }
        });
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void connectDB() {
        database = FirebaseDatabase.getInstance(DBURL);
        dbref = database.getReference("users");
    }
}

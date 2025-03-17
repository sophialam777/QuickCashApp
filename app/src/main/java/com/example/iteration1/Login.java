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

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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

    private void initializeLoginElements(){
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
            showError("All input fields need to be filled");
            return;
        }

        if (loginRole.equals("Select a role")) {
            showError("Please select a role");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()) {
            showError("Invalid email format");
            return;
        }

        // Authenticate using Firebase Authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(loginEmail, loginPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Get user info
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser != null) {
                            // Fetch user details from Firebase Realtime Database
                            dbref.orderByChild("email").equalTo(loginEmail)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                                    userAccount user = userSnapshot.getValue(userAccount.class);

                                                    if (user != null) {
                                                        if (!user.getRole().equals(loginRole)) {
                                                            showError("Incorrect role selected");
                                                            return;
                                                        }

                                                        Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_LONG).show();
                                                        new UserSession(user.getName(), user.getEmail(), user.getContact(), user.getRole(), user.getUserID());

                                                        Intent intent = new Intent(Login.this, homepage.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            } else {
                                                showError("Email not linked to an account");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            showError("Database error: " + error.getMessage());
                                        }
                                    });
                        }
                    } else {
                        showError("Incorrect email or password.");
                    }
                });
    }

    private void showError(String message){
        errorMessage.setText(message);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void connectDB(){
        database = FirebaseDatabase.getInstance(DBURL);
        dbref = database.getReference("users");
    }
}
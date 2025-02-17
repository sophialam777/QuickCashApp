package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.validator.RegistrationValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth; // FirebaseAuth instance
    private FirebaseDatabase database;
    private DatabaseReference dbref;
    private String DBURL = "https://quickcash3130-4607d-default-rtdb.firebaseio.com/";

    private EditText etName, etEmail, etContact, etPassword;
    private Spinner spinnerRole;
    private LinearLayout errorLayout;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration); // Links to the registration page layout

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etContact = findViewById(R.id.et_contact);
        etPassword = findViewById(R.id.et_password);
        spinnerRole = findViewById(R.id.spinner_role);
        errorLayout = findViewById(R.id.error_layout);
        errorMessage = findViewById(R.id.error_message);

        // Bind the role options from strings.xml to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        Button btn_create_account = findViewById(R.id.btn_create_account);
        btn_create_account.setOnClickListener(view -> validateInputs());
    }

    private void validateInputs(){
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String role = spinnerRole.getSelectedItem().toString();

        errorLayout.setVisibility(View.GONE);

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contact) || TextUtils.isEmpty(password)){
            showError("All input fields need to be filled");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showError("Invalid email format");
            return;
        }

        if(contact.length() > 15 || contact.length() < 10){
            showError("Contact number must be 10-15 digits");
            return;
        }

        if (!validatePassword(password)) {
            return;
        }

        if(role.equals("Select Role")){
            showError("Please select a role");
            return;
        }

        // Create the account using Firebase Authentication
        createAccount(name, email, password, contact, role);
    }

    private void createAccount(String name, String email, String password, String contact, String role){
        // Connect to the Firebase Realtime Database
        connectDB();

        // Register user using Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // User account created successfully
                            FirebaseUser user = mAuth.getCurrentUser(); // Get the logged-in user

                            // Use Firebase Database to store additional information
                            String userID = user.getUid();
                            userAccount newAcc = new userAccount(name, email.toLowerCase(), password, contact, role, userID);

                            // Add the user details to Firebase Realtime Database
                            dbref.child("users").child(userID).setValue(newAcc)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Success, user added to database
                                                Toast.makeText(Registration.this, "Account created successfully!", Toast.LENGTH_SHORT).show();


                                                Email.sendConfirmationEmail(email, name);

                                                // Navigate to Login screen
                                                Intent intent = new Intent(Registration.this, Login.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                // Failed to add user to database
                                                showError("Failed to save user data. Please try again.");
                                            }
                                        }
                                    });
                        } else {
                            // Failed to create user
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration failed";
                            showError(errorMessage);
                        }
                    }
                });
    }

    private void showError(String message){
        errorMessage.setText(message);
        errorLayout.setVisibility(View.VISIBLE);
    }

    public boolean validatePassword(String password) {
        RegistrationValidator passwordValidator = new RegistrationValidator();
        if (!passwordValidator.isPasswordLongEnough(password)) {
            showError("Password must be at least 8 characters");
            return false;
        } else if (!passwordValidator.isLowercaseInPassword(password)) {
            showError("Password must contain at least one lowercase letter");
            return false;
        } else if (!passwordValidator.isUppercaseInPassword(password)) {
            showError("Password must contain at least one uppercase letter");
            return false;
        } else if (!passwordValidator.isDigitInPassword(password)) {
            showError("Password must contain at least one digit");
            return false;
        } else if (!passwordValidator.isSymbolInPassword(password)) {
            showError("Password must contain at least one of the following characters: !, @, #, $, %, or &");
            return false;
        } else {
            return true;
        }
    }

    private void connectDB(){
        database = FirebaseDatabase.getInstance(DBURL);
        dbref = database.getReference();
    }
}

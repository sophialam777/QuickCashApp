package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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



public class Registration extends AppCompatActivity {
    private EditText etName, etEmail, etContact, etPassword;
    private Spinner spinnerRole;
    private LinearLayout errorLayout;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration); // Links to the registration page layout

        // Initialize views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etContact = findViewById(R.id.et_contact);
        etPassword = findViewById(R.id.et_password);
        spinnerRole = findViewById(R.id.spinner_role);
        errorLayout = findViewById(R.id.error_layout);
        errorMessage = findViewById(R.id.error_message);


        //Takes the string array from role_options in strings.xml and binds it to the spinner
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

        if(contact.length() < 10){
            showError("Contact number must be at least 10 digits");
            return;
        }

        if(password.length() < 8){
            showError("Password must be at least 8 characters");
            return;
        }

        if(role.equals("Select Role")){
            showError("Please select a role");
            return;
        }

        // Send confirmation email
        Email.sendConfirmationEmail(email, name);

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Registration.this, MainActivity.class);
        startActivity(intent);
    }

    private void showError(String message){
        errorMessage.setText(message);
        errorLayout.setVisibility(View.VISIBLE);
    }

}
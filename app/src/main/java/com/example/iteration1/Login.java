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

    private void validateLogin(){
        String loginEmail = email.getText().toString().toLowerCase().trim();
        String loginPassword = password.getText().toString().trim();
        String loginRole = roleSpinner.getSelectedItem().toString();
        String userID;

        errorLayout.setVisibility(View.GONE);

        // connect to the firebase db
        connectDB();

        if(TextUtils.isEmpty(loginPassword) || TextUtils.isEmpty(loginEmail)){
            showError("All input fields need to be filled");
            return;
        }

        if(loginRole.equals("Select a role")){
            showError("Please select a role");
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches()){
            showError("Invalid email format");
            return;
        }

        // check if email is linked to an account
        /*dbref.orderByChild("email").equalTo(loginEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){ // if true, email has an account

                    userAccount user = snapshot.getValue(userAccount.class);
                    Log.d(TAG, user.getName());
                    // check if password is correct

                    // check if role is correct

                } else {
                    showError("Email is not linked to an account");
                    // successful login message still displays...
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });*/

        dbref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot daSnapshot : snapshot.getChildren()){
                    userAccount user = daSnapshot.getValue(userAccount.class);
                    if(user.getEmail().equals(loginEmail)){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })

        Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Login.this, MainActivity.class);
        //startActivity(intent); this causes an error
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
package com.example.iteration1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
        setContentView(R.layout.activity_main);
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

    private void initializeOnClickListener(){
        login = findViewById(R.id.login_button);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        role = roleOptions[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
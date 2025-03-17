package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class homepage extends AppCompatActivity {

    private Button myAccount;
    private Button loginButton;
    private Button jobListingsButton;
    private Button postJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeOnClickListener();
    }

    private void initializeOnClickListener() {
        myAccount = findViewById(R.id.edit_profile);
        myAccount.setOnClickListener(v -> {
            // check if user is logged in, otherwise send them to the login page
            if(UserSession.loggedIn) {
                Intent intent = new Intent(homepage.this, MyAccount.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(homepage.this, Login.class);
                startActivity(intent);
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, Login.class);
            startActivity(intent);
        });

        jobListingsButton = findViewById(R.id.job_postings);
        jobListingsButton.setOnClickListener(v -> {
            // Navigate to the JobListingsActivity when the button is clicked
            Intent intent = new Intent(homepage.this, JobListingsActivity.class);
            startActivity(intent);
        });

        postJob = findViewById(R.id.post_job_button);
        postJob.setOnClickListener(v -> {
            if(UserSession.role.equals("Employer")) {
                Intent intent = new Intent(homepage.this, PostJob.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Only Employers can access the Post Job feature", Toast.LENGTH_LONG).show();
            }
        });

    }
}

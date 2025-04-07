package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeDashboard extends AppCompatActivity {

    private Button myAccount, jobListingsButton, jobSearchButton, rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_dashboard);

        initializeOnClickListener();
    }

    private void initializeOnClickListener() {
        myAccount = findViewById(R.id.edit_profile_employee);
        myAccount.setOnClickListener(v -> {
            // Navigate to the Edit Profile Page when the button is clicked
            verifyLoggedIn();
            Intent intent = new Intent(EmployeeDashboard.this, MyAccount.class);
            startActivity(intent);
        });

        jobListingsButton = findViewById(R.id.job_postings);
        jobListingsButton.setOnClickListener(v -> {
            // Navigate to the JobListingsActivity when the button is clicked
            verifyLoggedIn();
            Intent intent = new Intent(EmployeeDashboard.this, JobListingsActivity.class);
            startActivity(intent);
        });

        jobSearchButton = findViewById(R.id.jobsearch_button);
        jobSearchButton.setOnClickListener(v -> {
            // Navigate to the JobSearchActivity when the button is clicked
            verifyLoggedIn();
            Intent intent = new Intent(EmployeeDashboard.this, JobSearchActivity.class);
            startActivity(intent);
        });

        rate = findViewById(R.id.employee_rate_btn);
        rate.setOnClickListener(v -> {
            Intent intent = new Intent(this, RatingActivity.class);
            startActivity(intent);
        });
    }

    // redirect the user to the login page if they're not logged in
    public void verifyLoggedIn(){
        if (!UserSession.loggedIn) {
            Intent intent = new Intent(EmployeeDashboard.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

}
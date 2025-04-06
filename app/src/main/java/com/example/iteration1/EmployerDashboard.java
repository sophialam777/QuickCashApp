package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmployerDashboard extends AppCompatActivity {

    private Button myAccount, payButton, reviewApplications, postJob, perferlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_dashboard);

        initializeOnClickListener();
    }

    private void initializeOnClickListener() {
        myAccount = findViewById(R.id.edit_profile_employer);
        myAccount.setOnClickListener(v -> {
            // Navigate to the Edit Profile Page when the button is clicked
            verifyLoggedIn();
            Intent intent = new Intent(EmployerDashboard.this, MyAccount.class);
            startActivity(intent);
        });

        postJob = findViewById(R.id.post_job_button);
        postJob.setOnClickListener(v -> {
            // Navigate to the Post Job Page when the button is clicked
            verifyLoggedIn();
            Intent intent = new Intent(EmployerDashboard.this, PostJob.class);
            startActivity(intent);
        });

        reviewApplications = findViewById(R.id.review_applications);
        reviewApplications.setOnClickListener(v -> {
            // Navigate to the Review Applications Page when the button is clicked
            verifyLoggedIn();
            Intent intent = new Intent(EmployerDashboard.this, MyPostings.class);
            startActivity(intent);
        });

        payButton = findViewById(R.id.payEmployee_button);
        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerDashboard.this, PaymentActivity.class);
            startActivity(intent);
        });

        perferlist = findViewById(R.id.plist);
        perferlist.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerDashboard.this, PreferredEmployeeActivity.class);
            startActivity(intent);

        });
    }

    // redirect the user to the login page if they're not logged in
    public void verifyLoggedIn(){
        if (!UserSession.loggedIn) {
            Intent intent = new Intent(EmployerDashboard.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}
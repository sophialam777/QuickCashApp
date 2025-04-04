package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;




import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.messaging.FirebaseMessaging;

public class homepage extends AppCompatActivity {

    private Button myAccount;
    private Button loginButton;
    private Button jobListingsButton;
    private Button postJob;
    private Button jobSearchButton;
    private Button payButton;
    private Button perferlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        if(UserSession.role.equals("Employee")) {
            FirebaseMessaging.getInstance().subscribeToTopic("jobs")
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("homepage", "Subscribed to job notifications successfully.");
                        } else {
                            Log.e("homepage", "Failed to subscribe to job notifications.");
                        }
                    });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("MainActivity", "Fetching FCM token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d("MainActivity", "FCM Token: " + token);
                });
        initializeOnClickListener();
    }

    private void initializeOnClickListener() {
        // "My Account" button (ID: edit_profile) navigates to MyAccount activity
        myAccount = findViewById(R.id.edit_profile);
        myAccount.setOnClickListener(v -> {
            if (UserSession.loggedIn) {
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
            Intent intent = new Intent(homepage.this, JobListingsActivity.class);
            startActivity(intent);
        });

        postJob = findViewById(R.id.post_job_button);
        postJob.setOnClickListener(v -> {
            if (UserSession.role.equals("Employer")) {
                Intent intent = new Intent(homepage.this, PostJob.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Only Employers can access the Post Job feature", Toast.LENGTH_LONG).show();
            }
        });

        perferlist = findViewById(R.id.plist);
        perferlist.setOnClickListener(v -> {
            if(UserSession.role.equals("Employer")) {
                Intent intent = new Intent(homepage.this, PreferredEmployeeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Only Employers can access the Post Job feature", Toast.LENGTH_LONG).show();
            }
        });

        jobSearchButton = findViewById(R.id.jobsearch_button);
        jobSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, JobSearchActivity.class);
            startActivity(intent);
        });

        payButton = findViewById(R.id.payEmployee_button);
        payButton.setOnClickListener(v -> {
            Intent intent = new Intent(homepage.this, PaymenActivity.class);
            startActivity(intent);
        });


    }
}

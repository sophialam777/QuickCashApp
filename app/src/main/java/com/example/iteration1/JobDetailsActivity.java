package com.example.iteration1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.validator.Job;

public class JobDetailsActivity extends AppCompatActivity {

    private TextView jobTitle, jobDescription, jobRequirements, jobInstructions;
    private Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        //Initialize the TextViews
        jobTitle = findViewById(R.id.job_title);
        jobDescription = findViewById(R.id.job_description);
        jobRequirements = findViewById(R.id.job_requirements);
        jobInstructions = findViewById(R.id.job_instructions);
        applyButton = findViewById(R.id.apply_button);

        //Get the job object passed via Intent
        Job selectedJob = (Job) getIntent().getSerializableExtra("job");

        if (selectedJob != null) {
            //Set the job details into the TextViews
            jobTitle.setText(selectedJob.getTitle());
            jobDescription.setText(selectedJob.getDescription());
            jobRequirements.setText(selectedJob.getRequirements());
            jobInstructions.setText(selectedJob.getInstructions());
        }

        //Handle Apply button click
        applyButton.setOnClickListener(v -> {
            Toast.makeText(JobDetailsActivity.this, "Application Process Started", Toast.LENGTH_SHORT).show();
        });
    }
}

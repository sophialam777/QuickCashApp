package com.example.iteration1;

import android.content.Intent;
import android.net.Uri;
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

        //Initialize the TextViews and Apply Button
        jobTitle = findViewById(R.id.job_title);
        jobDescription = findViewById(R.id.job_description);
        jobRequirements = findViewById(R.id.job_requirements);
        jobInstructions = findViewById(R.id.job_instructions);
        applyButton = findViewById(R.id.apply_button);

        //Get job object and set details
        Job selectedJob = (Job) getIntent().getSerializableExtra("job");
        if (selectedJob != null) {
            jobTitle.setText(selectedJob.getTitle());
            jobDescription.setText(selectedJob.getDescription());
            jobRequirements.setText(selectedJob.getRequirements());
            jobInstructions.setText(selectedJob.getInstructions());
        }

        //Handle Attach Resume button click
        findViewById(R.id.attach_resume_button).setOnClickListener(v -> openFilePicker());

        //Handle Apply button click
        applyButton.setOnClickListener(v -> handleApplyButtonClick());
    }

    //Open file picker to select resume (*/* means Any type of file)
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/pdf", "application/msword"});
        startActivityForResult(intent, 1);
    }

    private void handleApplyButtonClick() {
        Toast.makeText(this, "Application Submitted!", Toast.LENGTH_SHORT).show();
    }

    //Handle result after file selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri resumeUri = data.getData();
            if (resumeUri != null) {
                Toast.makeText(this, "Resume selected: " + resumeUri.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

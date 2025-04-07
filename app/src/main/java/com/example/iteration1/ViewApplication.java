package com.example.iteration1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.validator.Job;

public class ViewApplication extends AppCompatActivity {

    private JobApplication selectedApplication;
    private Job selectedJob;
    private Button shortlist, reject, back, sendOffer, resume, confirmHire;
    private TextView questions, jobTitle, applicant, applicantEmail, status;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.application_detail);

        initalizeVariables();
        initalizeOnClickListeners();

        // replace the shortlist button with offer job if application is shortlisted
        if (selectedApplication.getStatus().equals("Shortlisted") || selectedApplication.getStatus().equals("Offered Job") || selectedApplication.getStatus().equals("Offer Rejected")) {
            sendOffer.setVisibility(View.VISIBLE);
            shortlist.setVisibility(View.INVISIBLE);
            confirmHire.setVisibility(View.INVISIBLE);
        } else if (selectedApplication.getStatus().equals("Offer Accepted")){
            reject.setVisibility(View.INVISIBLE);
            sendOffer.setVisibility(View.INVISIBLE);
            shortlist.setVisibility(View.INVISIBLE);
            confirmHire.setVisibility(View.VISIBLE);
        }
    }

    private void initalizeVariables(){
        selectedApplication = (JobApplication) getIntent().getSerializableExtra("application");
        selectedJob = (Job) getIntent().getSerializableExtra("job");
        questions = findViewById(R.id.questions_answers);
        applicant = findViewById(R.id.applicant_name);
        jobTitle = findViewById(R.id.job_title);
        applicantEmail = findViewById(R.id.applicant_email);
        back = findViewById(R.id.backButton);
        resume = findViewById(R.id.view_resume_button);
        shortlist = findViewById(R.id.shortlist_button);
        reject = findViewById(R.id.reject_button);
        status = findViewById(R.id.application_status);
        sendOffer = findViewById(R.id.send_offer_button);
        confirmHire = findViewById(R.id.confirm_offer_button);

        // update textviews with application information
        jobTitle.setText(selectedApplication.getJobTitle());
        applicant.setText(selectedApplication.getApplicantName());
        applicantEmail.setText(selectedApplication.getApplicantEmail());
        status.setText("Status: " + selectedApplication.getStatus());

        String questionsOutput = "None";
        if (selectedApplication.getAnswer1() != null) {
            questionsOutput = "Q1: " + selectedApplication.getAnswer1() + "\n";
            if (selectedApplication.getAnswer2() != null) {
                questionsOutput += "Q2: " + selectedApplication.getAnswer2();
            }
        }
        questions.setText(questionsOutput);
    }

    private void initalizeOnClickListeners(){
        back.setOnClickListener(v -> {
            // Navigate back to the application list
            Intent intent = new Intent(ViewApplication.this, ReceivedApplications.class);
            intent.putExtra("job", selectedJob);
            startActivity(intent);
        });

        resume.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(selectedApplication.getResumeUri()), getContentResolver().getType(Uri.parse(selectedApplication.getResumeUri())));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setData(Uri.parse(selectedApplication.getResumeUri()));
            startActivity(intent);
        });

        reject.setOnClickListener(v -> confirmRejection());

        shortlist.setOnClickListener(v -> {
            selectedApplication.setStatus("Shortlisted");

            Intent intent = new Intent(ViewApplication.this, ReceivedApplications.class);
            intent.putExtra("application", selectedApplication);
            startActivity(intent);
        });

        sendOffer.setOnClickListener(v -> {
            // redirect user to offer job page, send the application to the page
            Intent intent = new Intent(ViewApplication.this, OfferJob.class);
            intent.putExtra("application", selectedApplication);
            intent.putExtra("job", selectedJob);
            startActivity(intent);
        });

        confirmHire.setOnClickListener(v -> {
            selectedApplication.setStatus("Hired");
            Toast.makeText(this, "Hiring Confirmed!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ViewApplication.this, EmployerDashboard.class);
            startActivity(intent);
        });
    }

    private void confirmRejection(){
        // create a popup for the user to confirm their decision
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reject Application");
        builder.setMessage("Are you sure you want to reject this application?\nThis cannot be undone.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            // reject the application if yes is pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ViewApplication.this, "Application Rejected", Toast.LENGTH_LONG).show();
                selectedApplication.setStatus("Rejected");
                Intent intent = new Intent(ViewApplication.this, ReceivedApplications.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            // do nothing if no is pressed
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.validator.Job;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OfferJob extends AppCompatActivity {

    private Button back, offerJob;
    private TextView applicantInfo, jobTitle, errorMessage;
    private JobApplication selectedApplication;
    private EditText startDateIn, salaryIn, locationIn, otherIn;
    private Job selectedJob;

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.offer_job);

        selectedApplication = (JobApplication)getIntent().getSerializableExtra("application");
        selectedJob = (Job)getIntent().getSerializableExtra("job");

        back = findViewById(R.id.offer_job_back_button);
        offerJob = findViewById(R.id.offer_job_button);
        applicantInfo = findViewById(R.id.applicant_info);
        jobTitle = findViewById(R.id.job_title);
        errorMessage = findViewById(R.id.error_message);

        startDateIn = findViewById(R.id.start_date_in);
        salaryIn = findViewById(R.id.salary_in);
        locationIn = findViewById(R.id.location_in);
        otherIn = findViewById(R.id.other_in);

        applicantInfo.setText(selectedApplication.getApplicantInfo());
        jobTitle.setText(selectedApplication.getJobTitle());

        back.setOnClickListener(v -> {
            Intent intent = new Intent(OfferJob.this, ViewApplication.class);
            startActivity(intent);
        });

        offerJob.setOnClickListener(v -> {
            validateInputs();

        });
    }

    private void validateInputs(){
        String startDate = startDateIn.getText().toString().trim();
        String salary = salaryIn.getText().toString().trim();
        String location = locationIn.getText().toString().trim();
        String other = otherIn.getText().toString().trim();


        if(TextUtils.isEmpty(startDate) || TextUtils.isEmpty(salary) || TextUtils.isEmpty(location)){
            showError("At least one required input field was not filled");
        } else if(!validateStartDate(startDate)){
            showError("Start Date is invalid, ensure it follows MM/DD/YYYY formatting");
        } else {

            createJobOffer(startDate, salary, location, other);
        }
    }

    private boolean validateStartDate(String startDate){
        // regex to verify date is formatted properly & an actual date
        String regex = "^(?:(0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01])|"
                + "(0[469]|11)/(0[1-9]|[12][0-9]|30)|"
                + "02/(0[1-9]|1[0-9]|2[0-8]))/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(startDate);
        return matcher.matches();
    }

    private void createJobOffer(String startDate, String salary, String location, String other){
        DatabaseReference jobOffersRef = FirebaseDatabase.getInstance().getReference("jobOffers");
        String jobOfferID = jobOffersRef.push().getKey();

        JobOffer offer = new JobOffer(salary, startDate, location, other, "Pending", selectedApplication.getApplicationID());

        if(jobOfferID != null){
            jobOffersRef.child(jobOfferID).setValue(offer);
            Toast.makeText(this, "Offer Sent Successfully", Toast.LENGTH_LONG).show();
            selectedApplication.setStatus("Offered Job");
            Intent intent = new Intent(OfferJob.this, ReceivedApplications.class);
            intent.putExtra("job", selectedJob);
            startActivity(intent);
        }
    }

    private void showError(String message){
        errorMessage.setText(message);
        errorMessage.setVisibility(View.VISIBLE);
    }
}
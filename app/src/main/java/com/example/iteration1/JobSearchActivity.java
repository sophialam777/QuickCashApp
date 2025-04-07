package com.example.iteration1;
import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class JobSearchActivity extends AppCompatActivity {

        private String[] jobTypeOptions = {"Any" ,"Part-time","Full-time"};
        private String[] minSalaryOptions = {"Any","$9/hr","$10/hr","$11/hr","$12/hr","$13/hr","$14/hr","$15/hr","$16/hr","$17/hr","$18/hr","$19/hr","$20/hr"};
        private String[] locationOptions = { "Any","Online","Halifax","Fairview (North End)","North End Halifax","Chain Lake","Point Pleasant Park","Dalhousie University","Larry Uteck","Bedford","Halifax Shopping Centre","St. Mary’s University"};
        private Spinner jobTypeSpinner, minSalarySpinner, locationSpinner;
        private Button searchButton, backButton;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.jobsearch);
                initializeSpinners();
                initializeButtons();
        }

        private void initializeSpinners(){
                jobTypeSpinner = findViewById(R.id.job_type);
                minSalarySpinner = findViewById(R.id.min_salary);
                locationSpinner = findViewById(R.id.location);
                ArrayAdapter<String>  jobTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, jobTypeOptions);
                ArrayAdapter<String> minSalaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, minSalaryOptions);
                ArrayAdapter<String>  locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, locationOptions);
                jobTypeSpinner.setAdapter(jobTypeAdapter);
                minSalarySpinner.setAdapter(minSalaryAdapter);
                locationSpinner.setAdapter( locationAdapter);
        }

        private void initializeButtons(){
                backButton = findViewById(R.id.go_back_button);
                searchButton= findViewById(R.id.search_button);

                //back button eventlisnter
                backButton .setOnClickListener(v -> {
                        // Navigate to the homepage when the button is clicked
                        Intent intent = new Intent(JobSearchActivity.this, EmployeeDashboard.class);
                        startActivity(intent);
                });

                //search button eventlistener
                searchButton.setOnClickListener(v -> {
                        Intent intent = new Intent(JobSearchActivity.this, SearchResultActivity.class);
                        String [] selected = getSearchCondition();
                        intent.putExtra("selectedOption", selected);
                        startActivity(intent);
                });

        }

        private String[] getSearchCondition(){
                //get chosen option
                String chosenJobType = jobTypeSpinner.getSelectedItem().toString();
                String chosenMinSalary = minSalarySpinner.getSelectedItem().toString();
                String chosenLocation = locationSpinner.getSelectedItem().toString();

                String[] chosenOptions = {chosenJobType ,chosenMinSalary, chosenLocation};
                return chosenOptions;
        }
}
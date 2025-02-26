package com.example.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.iteration1.validator.Job;

import java.util.ArrayList;

public class JobListingsActivity extends AppCompatActivity {

    private ListView jobListView;
    private ArrayList<Job> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listings);

        jobListView = findViewById(R.id.job_list_view);

        //Initialize job list
        jobList = new ArrayList<>();
        jobList = new ArrayList<>();
        jobList.add(new Job("Food Delivery Driver", "Deliver food to customers", "Valid driver's license, car or bike", "Apply by submitting your resume"));
        jobList.add(new Job("Ride-Share Driver", "Provide rides to passengers", "Valid driver's license, car", "Apply by submitting your resume"));
        jobList.add(new Job("Freelance Writer", "Write content for blogs and websites", "Strong writing skills, basic research abilities", "Apply by submitting your resume"));
        jobList.add(new Job("Virtual Assistant", "Provide administrative support remotely", "Organizational skills, good communication", "Apply by submitting your resume"));
        jobList.add(new Job("Online Tutor", "Teach students various subjects online", "Expertise in a subject, teaching skills", "Apply by submitting your resume"));
        jobList.add(new Job("Customer Service Representative", "Handle customer inquiries and support", "Good communication skills, patience", "Apply by submitting your resume"));
        jobList.add(new Job("Survey Taker", "Participate in online surveys", "No special qualifications", "Apply by submitting your resume"));
        jobList.add(new Job("Pet Sitter/Dog Walker", "Take care of pets or walk dogs", "Love for animals and be responsible", "Apply by submitting your resume"));
        jobList.add(new Job("Grocery Shopper", "Shop and deliver groceries to customers", "Ability to shop, reliable transportation", "Apply by submitting your resume"));
        jobList.add(new Job("Data Entry Clerk", "Enter data into systems or spreadsheets", "Attention to detail, basic computer skills", "Apply by submitting your resume"));
        jobList.add(new Job("Freelance Graphic Designer", "Design logos, flyers, or social media graphics", "Graphic design skills, familiarity with design tools", "Apply by submitting your resume"));
        jobList.add(new Job("House Cleaner", "Clean homes or offices", "Reliable and attention to detail", "AApply by submitting your resume"));
        jobList.add(new Job("Photography Assistant", "Assist photographers during shoots", "Interest in photography and have organizational skills", "Apply by submitting your resume"));
        jobList.add(new Job("Retail Associate", "Work part-time in stores and assist customers", "Customer service skills and be willing to work weekends", "Apply by submitting your resume"));
        jobList.add(new Job("Event Staff", "Help at events, conferences, or parties", "Ability to follow instructions and have a flexible schedule", "Apply by submitting your resume"));

        // Set up the adapter to display jobs
        ArrayAdapter<Job> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList);
        jobListView.setAdapter(adapter);

        // Handle item click to show job details
        jobListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Job selectedJob = jobList.get(position);
            Intent intent = new Intent(JobListingsActivity.this, JobDetailsActivity.class);
            intent.putExtra("job", selectedJob);  // Pass the selected job
            startActivity(intent);
        });
    }
}

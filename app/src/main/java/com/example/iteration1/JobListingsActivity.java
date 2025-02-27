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

        jobList = new ArrayList<>();
        jobList.add(new Job("Food Delivery Driver",
                "Deliver food to customers within a specific time frame from various restaurants to their homes or offices.",
                "• Valid driver’s license (Class C)\n• Reliable vehicle (car or bike)\n• Smartphone with navigation app\n",
                "Please submit your resume."));

        //Ride-Share Driver
        jobList.add(new Job("Ride-Share Driver",
                "Provide rides to passengers using a ride-sharing app, ensuring a safe and comfortable journey to their destinations.",
                "• Valid driver’s license (Class C)\n• Must pass a background check\n• Ability to provide excellent customer service.",
                "Please submit your resume."));

        //Freelance Writer
        jobList.add(new Job("Freelance Writer",
                "Write content for blogs, websites and marketing materials",
                "• Strong writing and editing skills\n• Ability to research a variety of topics\n• Ability to meet deadlines and work independently",
                "Please attach your resume."));

        //Virtual Assistant
        jobList.add(new Job("Virtual Assistant",
                "Provide remote administrative support, manage calendars, respond to emails, handle customer inquiries, and perform various office tasks.",
                "• Excellent written and verbal communication\n• Ability to manage multiple tasks and priorities",
                "Please submit your resume."));

        //Online Tutor
        jobList.add(new Job("Online Tutor",
                "Teach students various subjects online through video calls, live sessions, and coursework help.",
                "• Expertise in one or more subjects (Math, Science, Language Arts, etc.)\n• Ability to explain complex concepts in an easy-to-understand manner",
                "Please submit your resume."));

        //Customer Service Representative
        jobList.add(new Job("Customer Service Representative",
                "Handle customer inquiries, complaints, and support requests via phone, email, or live chat.",
                "• Excellent communication and problem-solving skills\n• Ability to remain calm under pressure\n• Previous customer service experience preferred",
                "Please submit your resume."));

        //Survey Taker
        jobList.add(new Job("Survey Taker",
                "Participate in online surveys and market research, providing feedback on products, services, and general opinions.",
                "• Must be honest and thoughtful in providing feedback\n• Ability to meet deadlines for survey completion.",
                "Please submit your resume."));

        //Pet Sitter/Dog Walker
        jobList.add(new Job("Pet Sitter/Dog Walker",
                "Take care of pets, including walking dogs, feeding them, and providing company.",
                "• Love and care for animals\n• Responsible and reliable\n• Previous experience with animals preferred",
                "Please submit your resume."));

        //Grocery Shopper
        jobList.add(new Job("Grocery Shopper",
                "Shop and deliver groceries to customers. You will be required to pick items from a list, follow the customer's preferences, and ensure timely delivery.",
                "• Ability to shop and follow instructions on grocery lists\n• Reliable transportation for deliveries\n• Ability to carry heavy grocery bags and handle cold or frozen items",
                "Please submit your resume."));

        //Data Entry Clerk
        jobList.add(new Job("Data Entry Clerk",
                "Enter data into systems or spreadsheets, ensuring accuracy and timely updates. The role involves working with large volumes of data and performing routine data integrity checks.",
                "• Attention to detail\n• Ability to work with spreadsheets (Excel, Google Sheets, etc.)\n• Basic knowledge of data management systems",
                "Please submit your resume."));

        //Freelance Graphic Designer
        jobList.add(new Job("Freelance Graphic Designer",
                "Design logos, marketing materials, social media posts, and website graphics. Provide creative designs that align with client branding and business goals.",
                "• Graphic design skills (Adobe Illustrator, Photoshop, etc.)\n• Experience designing for print and digital media\n• Ability to meet deadlines and communicate with clients effectively.",
                "Please submit your resume."));

        //House Cleaner
        jobList.add(new Job("House Cleaner",
                "Clean homes or offices, including tasks like vacuuming, mopping, dusting, and organizing. May include deep cleaning services on request.",
                "• Ability to clean efficiently and to a high standard\n• Attention to detail\n• Ability to work independently\n• Previous cleaning experience is a plus.",
                "Please submit your resume."));

        //Photography Assistant
        jobList.add(new Job("Photography Assistant",
                "Assist photographers during shoots by setting up equipment, managing props, and helping with lighting and camera settings. E",
                "• Interest in photography\n• Organizational skills\n• Basic understanding of photography equipment\n• Ability to work long hours and on weekends when required.",
                "Please submit your resume."));

        //Retail Associate
        jobList.add(new Job("Retail Associate",
                "Work part-time in stores and assist customers with product selection, process transactions, and help maintain store organization.",
                "• Customer service skills\n• Ability to work in a fast-paced environment\n• Willingness to work weekends and evenings\n• Previous retail experience is a plus.",
                "Please submit your resume."));

        //Event Staff
        jobList.add(new Job("Event Staff",
                "Help with setup, coordination, and breakdown at events, conferences, or parties.",
                "• Strong organizational skills\n• Flexibility and willingness to work evenings and weekends\n• Previous event staff experience is a plus.",
                "Please submit your resume."));


        //Set up the adapter to display jobs
        ArrayAdapter<Job> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobList);
        jobListView.setAdapter(adapter);

        //Handle item click to show job details
        jobListView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Job selectedJob = jobList.get(position);
            Intent intent = new Intent(JobListingsActivity.this, JobDetailsActivity.class);
            intent.putExtra("job", selectedJob);  //Pass the selected job
            startActivity(intent);
        });
    }
}

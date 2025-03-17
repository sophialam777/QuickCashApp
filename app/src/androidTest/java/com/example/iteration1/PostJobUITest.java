package com.example.iteration1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PostJobUITest {

    @Rule
    public ActivityScenarioRule<PostJob> activityRule = new ActivityScenarioRule<>(PostJob.class);

    @Test
    public void testMissingRequiredField(){
        // submit an empty form
        onView(withId(R.id.post_job_button)).perform(click());

        // verify the toast displays the proper error message
        assertEquals("Please fill all mandatory fields.", PostJob.toast_msg);
    }

    @Test
    public void testRequiredFieldsFilled(){
        // fill in all input fields
        onView(withId(R.id.job_title_input)).perform(typeText("Job Title"), closeSoftKeyboard());
        onView(withId(R.id.job_location_input)).perform(typeText("Location"), closeSoftKeyboard());
        onView(withId(R.id.job_type_input)).perform(typeText("Part Time"), closeSoftKeyboard());
        onView(withId(R.id.job_pay_input)).perform(typeText("$20/hour"), closeSoftKeyboard());
        onView(withId(R.id.job_description_input)).perform(typeText("do something"), closeSoftKeyboard());
        onView(withId(R.id.job_latitude_input)).perform(typeText("0.00"), closeSoftKeyboard());
        onView(withId(R.id.job_longitude_input)).perform(typeText(".00"), closeSoftKeyboard());
        onView(withId(R.id.job_questions_input)).perform(typeText("question 1, question 2"), closeSoftKeyboard());

        // submit job posting
        onView(withId(R.id.post_job_button)).perform(click());

        // verify the toast doesn't have a message
        assertEquals("", PostJob.toast_msg);
    }
    @Test
    public void verifyJobAppearsInDatabase(){
        // fill in all input fields
        onView(withId(R.id.job_title_input)).perform(typeText("Test Job"), closeSoftKeyboard());
        onView(withId(R.id.job_location_input)).perform(typeText("Location"), closeSoftKeyboard());
        onView(withId(R.id.job_type_input)).perform(typeText("Part Time"), closeSoftKeyboard());
        onView(withId(R.id.job_pay_input)).perform(typeText("$20/hour"), closeSoftKeyboard());
        onView(withId(R.id.job_description_input)).perform(typeText("do something"), closeSoftKeyboard());
        onView(withId(R.id.job_latitude_input)).perform(typeText("0.00"), closeSoftKeyboard());
        onView(withId(R.id.job_longitude_input)).perform(typeText(".00"), closeSoftKeyboard());
        onView(withId(R.id.job_questions_input)).perform(typeText("question 1, question 2"), closeSoftKeyboard());

        // submit job posting
        onView(withId(R.id.post_job_button)).perform(click());

        // initialize database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash3130-4607d-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = database.getReference("jobs");

        //Query the database to check if the email exists
        dbRef.orderByChild("title").equalTo("Test Job").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("Job found in database.");
                } else {
                    System.out.println("Job not found in database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Database error: " + databaseError.getMessage());
            }
        });
    }
}
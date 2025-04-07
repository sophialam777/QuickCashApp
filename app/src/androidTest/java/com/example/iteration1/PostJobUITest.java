package com.example.iteration1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PostJobUITest {

    @Rule
    public ActivityScenarioRule<PostJob> activityRule = new ActivityScenarioRule<>(PostJob.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testMissingRequiredField() {
        // submit an empty form
        onView(withId(R.id.post_job_button)).perform(click());

        // verify the toast displays the proper error message
        assertEquals("Please fill all mandatory fields.", PostJob.toast_msg);
    }

    @Test
    public void testRequiredFieldsFilled() {
        // fill in all input fields with valid data
        onView(withId(R.id.job_title_input)).perform(typeText("Job Title"), closeSoftKeyboard());
        onView(withId(R.id.job_location_input)).perform(typeText("123 Main St, Halifax, Canada"), closeSoftKeyboard());
        onView(withId(R.id.job_type_input)).perform(typeText("Part Time"), closeSoftKeyboard());
        onView(withId(R.id.job_pay_input)).perform(typeText("$20/hour"), closeSoftKeyboard());
        onView(withId(R.id.job_description_input)).perform(typeText("Job description"), closeSoftKeyboard());
        onView(withId(R.id.job_questions_input)).perform(typeText("question 1, question 2"), closeSoftKeyboard());

        // submit job posting
        onView(withId(R.id.post_job_button)).perform(click());

        // verify the toast doesn't have an error message
        assertNull(PostJob.toast_msg);
    }

    @Test
    public void testBackButtonNavigation() {
        // click the back button
        onView(withId(R.id.job_posting_back_button)).perform(click());

        // verify we navigate back to EmployerDashboard
        intended(hasComponent(EmployerDashboard.class.getName()));
    }

    @Test
    public void verifyJobAppearsInDatabase() throws InterruptedException {
        // fill in all input fields with test data
        String testJobTitle = "Test Job " + System.currentTimeMillis();
        onView(withId(R.id.job_title_input)).perform(typeText(testJobTitle), closeSoftKeyboard());
        onView(withId(R.id.job_location_input)).perform(typeText("123 Test St, Test City"), closeSoftKeyboard());
        onView(withId(R.id.job_type_input)).perform(typeText("Full Time"), closeSoftKeyboard());
        onView(withId(R.id.job_pay_input)).perform(typeText("$25/hour"), closeSoftKeyboard());
        onView(withId(R.id.job_description_input)).perform(typeText("Test description"), closeSoftKeyboard());
        onView(withId(R.id.job_questions_input)).perform(typeText("test question 1, test question 2"), closeSoftKeyboard());

        // submit job posting
        onView(withId(R.id.post_job_button)).perform(click());

        // initialize database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash3130-4607d-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = database.getReference("jobs");


        // Query the database to check if the job exists
        dbRef.orderByChild("title").equalTo(testJobTitle).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("Job found in database.");
                    assertEquals(true, dataSnapshot.exists());
                } else {
                    System.out.println("Job not found in database.");
                    assertEquals(false, true); // Force test failure
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Database error: " + databaseError.getMessage());
                assertEquals(false, true); // Force test failure
            }
        });
    }
}
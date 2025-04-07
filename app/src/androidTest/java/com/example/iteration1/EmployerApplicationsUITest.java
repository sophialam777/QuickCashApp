package com.example.iteration1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.iteration1.validator.Job;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class EmployerApplicationsUITest {

    @BeforeClass
    public static void setUpClass() {
        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    @Rule
    public ActivityScenarioRule<MyPostings> activityRule =
            new ActivityScenarioRule<>(MyPostings.class);

    @Before
    public void setUp() {
        // Initialize test user session
        UserSession.login("Test Employer", "test@employer.com", "1234567890",
                "Employer", "testUserId", "testFcmToken");

        // Create test job data
        Job testJob = new Job(
                "Test Job",
                "Halifax",
                "Test Description",
                "Full-time",
                "$20/hour",
                44.651070,
                -63.582687,
                Arrays.asList("Question 1", "Question 2"),
                "test@employer.com",
                "Test Employer"
        );

        // Add test data to Firebase
        FirebaseDatabase.getInstance().getReference("jobs").push().setValue(testJob);
    }

    @Test
    public void testBasicUIElementsDisplayed() throws InterruptedException {

        // Check title
        onView(withId(R.id.my_postings_title))
                .check(matches(isDisplayed()))
                .check(matches(withText("My Postings")));

        // Check list is displayed
        onView(withId(R.id.my_postings_list))
                .check(matches(isDisplayed()));

        // Check back button
        onView(withId(R.id.my_postings_back_button))
                .check(matches(isDisplayed()))
                .check(matches(withText("Back")));
    }
}
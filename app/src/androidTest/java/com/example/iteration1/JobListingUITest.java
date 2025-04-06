package com.example.iteration1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import com.example.iteration1.validator.Job;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class JobListingUITest {

    private Job testJob;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    );

    @Before
    public void setUp() {
        // Create test job with all required fields
        testJob = new Job(
                "Software Engineer",         // title
                "Halifax",                  // location
                "Develop applications",      // description
                "Full-time",                // type
                "$30/hour",                  // pay
                44.6452,                    // latitude
                -63.5736,                   // longitude
                Arrays.asList("Do you have a valid driver license?", "Do you have experience related to this job"), // questions
                "test@employer.com",        // poster's email
                "Test Employer"              // poster's name
        );
    }

    @Test
    public void jobDetailsDisplayed() {
        Intent intent = new Intent(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                JobDetailsActivity.class
        );
        intent.putExtra("job", testJob);

        try (ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(intent)) {
            // Verify all job details are displayed correctly
            onView(withId(R.id.job_title))
                    .check(matches(withText("Software Engineer")));

            onView(withId(R.id.job_loc))
                    .check(matches(withText(containsString("Halifax"))));

            onView(withId(R.id.job_description))
                    .check(matches(withText("Develop applications")));

            onView(withId(R.id.job_type))
                    .check(matches(withText(containsString("Full-time"))));

            onView(withId(R.id.job_pay))
                    .check(matches(withText(containsString("$30/hour"))));

        }
    }
}
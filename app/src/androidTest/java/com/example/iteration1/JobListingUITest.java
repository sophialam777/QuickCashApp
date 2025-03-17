package com.example.iteration1;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onIdle;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasToString;

import android.content.Intent;
import android.net.Uri;

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
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);

    @Before
    public void setUp() {
        // Provide the correct parameters for the new constructor
        testJob = new Job(
                "Software Engineer",         // title
                "Halifax",                   // location
                "Develop applications",      // description
                "Full-time",                 // type
                "$30/hour",                  // pay
                44.6452,                     // latitude
                -63.5736,                    // longitude
                Arrays.asList("Driver license?", "Due date?")
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
            // Title
            onView(withId(R.id.job_title)).check(matches(withText("Software Engineer")));
            // Location (prefix "Location: ")
            onView(withId(R.id.job_loc)).check(matches(withText("Location: Halifax")));
            // Description
            onView(withId(R.id.job_description)).check(matches(withText("Develop applications")));
            // Type (prefix "Type: ")
            onView(withId(R.id.job_type)).check(matches(withText("Type: Full-time")));
            // Pay (prefix "Pay: ")
            onView(withId(R.id.job_pay)).check(matches(withText("Pay: $30/hour")));
        }
    }

    @Test
    public void testApplyButtonShowsToastWhenResumeAttached() {
        // Launch the activity
        Intent intent = new Intent(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                JobDetailsActivity.class
        );
        intent.putExtra("job", testJob);

        try (ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(intent)) {
            // Simulate attaching a resume
            scenario.onActivity(activity -> activity.selectedResumeUri = Uri.parse("content://fake/path/to/resume.pdf"));

            // Provide an answer to the first question
            onView(withId(R.id.question1_input)).perform(typeText("Yes, I have a license"));
            closeSoftKeyboard();

            // Click apply
            onView(withId(R.id.apply_button)).perform(click());

            // Check for success toast
            onView(withText("Application Submitted!"))
                    .inRoot(withDecorView(not(scenario.waitForActivity().getWindow().getDecorView())))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void errorShownWhenNoResumeSelected() {
        // Launch the activity
        Intent intent = new Intent(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                JobDetailsActivity.class
        );
        intent.putExtra("job", testJob);

        try (ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(intent)) {
            // No resume attached
            // Provide the required question answer
            onView(withId(R.id.question1_input)).perform(typeText("Yes, I have a license"));
            closeSoftKeyboard();

            // Click apply
            onView(withId(R.id.apply_button)).perform(click());

            // Verify the toast message
            onView(withText("Please attach a resume before submitting your application."))
                    .inRoot(withDecorView(not(scenario.waitForActivity().getWindow().getDecorView())))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void errorShownWhenRequiredQuestionIsEmpty() {
        // Launch the activity
        Intent intent = new Intent(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                JobDetailsActivity.class
        );
        intent.putExtra("job", testJob);

        try (ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(intent)) {
            // Simulate attaching a resume
            scenario.onActivity(activity -> activity.selectedResumeUri = Uri.parse("content://fake/path/to/resume.pdf"));

            // Leave question1_input empty
            onView(withId(R.id.apply_button)).perform(click());

            // Verify the toast message
            onView(withText("Please answer all required questions."))
                    .inRoot(withDecorView(not(scenario.waitForActivity().getWindow().getDecorView())))
                    .check(matches(isDisplayed()));
        }
    }
}

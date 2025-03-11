package com.example.iteration1;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasToString;

import android.content.Intent;
import android.net.Uri;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import com.example.iteration1.JobDetailsActivity;
import com.example.iteration1.validator.Job;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobListingUITest {

    private Job testJob;

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);

    @Before
    public void setUp() {
        String[] questions = {"Driver license?", "Due?"}; // Define job-specific questions
        testJob = new Job(
                "Software Engineer",       // title
                "Develop applications",    // description
                "Java, Android",           // requirements
                "Apply online",            // instructions
                44.6452,                  // latitude
                -63.5736,                 // longitude
                questions                  // questions
        );
    }

    @Test
    public void JobDetailsDisplayed() {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), JobDetailsActivity.class);
        intent.putExtra("job", testJob);
        ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(intent);

        onView(withId(R.id.job_title)).check(matches(withText("Software Engineer")));
        onView(withId(R.id.job_description)).check(matches(withText("Develop applications")));
        onView(withId(R.id.job_requirements)).check(matches(withText("Java, Android")));
        onView(withId(R.id.job_instructions)).check(matches(withText("Apply online")));
    }

    @Test
    public void testApplyButtonShowsToast() {
        ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), JobDetailsActivity.class));
        scenario.onActivity(activity -> activity.selectedResumeUri = Uri.parse("content://fake/path/to/resume.pdf"));
        onView(withId(R.id.apply_button)).perform(click());
        onView(withText("Application Submitted!"));

    }

    @Test
    public void AttachResumeOpensFilePicker() {
        ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), JobDetailsActivity.class));
        onView(withId(R.id.attach_resume_button)).perform(click());
    }
    @Test
    public void ErrorShownWhenNoResumeSelected() {
        //Test Fails
        ActivityScenario<JobDetailsActivity> scenario = ActivityScenario.launch(
                new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), JobDetailsActivity.class)
        );
        onView(withId(R.id.apply_button)).perform(click());
        scenario.onActivity(activity -> {
            try {
                onView(withText("Please Select a Resume"))
                        .inRoot(withDecorView(not(activity.getWindow().getDecorView())))
                        .check(matches(withText("Please Select a Resume")));
            } catch (Exception e) {
                throw new AssertionError("Test failed: 'Please Select a Resume' was not shown.");
            }
        });
    }
    @Test
    public void UserLoginAndViewJobDetails() throws InterruptedException {
        ActivityScenario.launch(new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), Login.class));

        onView(withId(R.id.login_email)).perform(typeText("mohammedzaksajan@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("Zakizaki1!"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        Thread.sleep(2000);


        onView(withId(R.id.job_postings)).check(matches(isDisplayed()));


        onView(withId(R.id.job_postings)).perform(click());


        onData(hasToString(containsString("Online Tutor"))).perform(click());


        onView(withId(R.id.job_title)).check(matches(withText("Online Tutor")));
        onView(withId(R.id.attach_resume_button)).perform(click());
    }

}

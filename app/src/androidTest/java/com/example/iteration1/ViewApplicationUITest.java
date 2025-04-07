package com.example.iteration1;

import android.content.Intent;
import android.net.Uri;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.iteration1.validator.Job;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class ViewApplicationUITest {

    private JobApplication testApplication;
    private Job testJob;

    @Rule
    public IntentsTestRule<ViewApplication> activityRule =
            new IntentsTestRule<>(ViewApplication.class, false, false);

    @Before
    public void setUp() {
        // Create test data with all required fields
        testJob = new Job(
                "Test Job",
                "Test Location",
                "Test Description",
                "Full-time",
                "$20/hr",
                0.0,
                0.0,
                Arrays.asList("Question 1", "Question 2"),
                "employer@test.com",  // Make sure posterEmail is set
                "Test Employer"
        );

        testApplication = new JobApplication(
                "app123",
                "Test Job",
                "employer@test.com",  // posterEmail matches job's posterEmail
                "applicant@test.com",
                "content://test/resume.pdf",
                "Answer 1",
                "Answer 2",
                "Test Applicant",
                "Pending",
                "fcmToken123"
        );

        // Launch activity with test intent
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewApplication.class);
        intent.putExtra("application", testApplication);
        intent.putExtra("job", testJob);
        activityRule.launchActivity(intent);
    }

    @Test
    public void testUIElementsDisplayCorrectly() {
        // Verify job title is displayed
        onView(withId(R.id.job_title))
                .check(matches(withText("Test Job")));

        // Verify applicant name is displayed
        onView(withId(R.id.applicant_name))
                .check(matches(withText("Test Applicant")));

        // Verify applicant email is displayed
        onView(withId(R.id.applicant_email))
                .check(matches(withText("applicant@test.com")));

        // Verify questions and answers are displayed
        onView(withId(R.id.questions_answers))
                .check(matches(withText(containsString("Q1: Answer 1"))))
                .check(matches(withText(containsString("Q2: Answer 2"))));
    }
    @Test
    public void testBackButtonNavigatesToReceivedApplications() {
        // Click back button
        onView(withId(R.id.backButton))
                .perform(click());

        intended(hasComponent(ReceivedApplications.class.getName()));

    }

    @Test
    public void testResumeButton() {
        // Click resume button
        onView(withId(R.id.view_resume_button))
                .perform(click());

        // Verify intent to view resume is launched
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse(testApplication.getResumeUri()))
        ));
    }


    @Test
    public void testRejectButtonShowsConfirmationDialog() {
        // Click reject button
        onView(withId(R.id.reject_button))
                .perform(click());

        // Verify dialog title
        onView(withText("Reject Application"))
                .check(matches(isDisplayed()));

        // Verify dialog message (partial match)
        onView(withText(containsString("Are you sure you want to reject this application?")))
                .check(matches(isDisplayed()));

        // Verify dialog buttons
        onView(withText("Yes"))
                .check(matches(isDisplayed()));
        onView(withText("No"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUIChangesForShortlistedStatus() {
        // Create shortlisted application
        testApplication.setStatus("Shortlisted");
        activityRule.finishActivity();

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewApplication.class);
        intent.putExtra("application", testApplication);
        intent.putExtra("job", testJob);
        activityRule.launchActivity(intent);

        // Verify send offer button is visible
        onView(withId(R.id.send_offer_button))
                .check(matches(isDisplayed()));

        // Verify shortlist button is hidden
        onView(withId(R.id.shortlist_button))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void testUIChangesForOfferAcceptedStatus() {
        // Create offer accepted application
        testApplication.setStatus("Offer Accepted");
        activityRule.finishActivity();

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewApplication.class);
        intent.putExtra("application", testApplication);
        intent.putExtra("job", testJob);
        activityRule.launchActivity(intent);

        // Verify confirm hire button is visible
        onView(withId(R.id.confirm_offer_button))
                .check(matches(isDisplayed()));

        // Verify other action buttons are hidden
        onView(withId(R.id.reject_button))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.send_offer_button))
                .check(matches(not(isDisplayed())));

        onView(withId(R.id.shortlist_button))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void testSendOfferButtonNavigatesToOfferJob() {
        // Set status to Shortlisted to show offer button
        testApplication.setStatus("Shortlisted");
        activityRule.finishActivity();

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ViewApplication.class);
        intent.putExtra("application", testApplication);
        intent.putExtra("job", testJob);
        activityRule.launchActivity(intent);

        // Click send offer button
        onView(withId(R.id.send_offer_button))
                .perform(click());

        // Verify navigation to OfferJob with correct data
        intended(allOf(
                hasComponent(OfferJob.class.getName()),
                hasExtraWithKey("application"),
                hasExtraWithKey("job")
        ));
    }
}
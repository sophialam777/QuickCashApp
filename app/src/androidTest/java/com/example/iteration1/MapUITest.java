package com.example.iteration1;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import com.example.iteration1.JobListingsActivity;
import com.example.iteration1.GoogleMapsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MapUITest {

    @Rule
    public ActivityTestRule<JobListingsActivity> jobListingsActivityTestRule =
            new ActivityTestRule<>(JobListingsActivity.class);

    @Test
    public void testJobListingsActivity() {
        // Checking if "View Jobs on Map" button is displayed and clickable
        onView(withId(R.id.view_map_button)).check(matches(isDisplayed()));
        onView(withId(R.id.view_map_button)).perform(click());

        // Check if job list is displayed
        onView(withId(R.id.job_list_view)).check(matches(isDisplayed()));
    }
    @Test
    public void testGoogleMapsActivity() {
        // Check if Google Maps fragment is displayed
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }
    @Test
    public void testMarkerClickOpensJobDetailsDialog() {
        GoogleMapsActivity activity = googleMapsActivityTestRule.getActivity();

        // Add a test marker programmatically (mocking a job marker)
        activity.runOnUiThread(() -> {
            LatLng testLocation = new LatLng(44.6452, -63.5736);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(testLocation)
                    .title("Test Job");
            activity.mMap.addMarker(markerOptions);
        });

        // Click the marker (Espresso doesn't support map interactions, so verification might be needed)
        onView(withText("Test Job")).check(matches(isDisplayed()));

        // Verify that the job details dialog appears with expected title
        onView(withText("Job Details")).check(matches(isDisplayed()));
    }
}

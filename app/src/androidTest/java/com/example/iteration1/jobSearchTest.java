package com.example.iteration1;

import androidx.test.core.app.ActivityScenario;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;



@RunWith(AndroidJUnit4.class)
public class jobSearchTest {
    ActivityScenario<JobSearchActivity> activityScenario;


    @Test
    public void CriteriaAreDisplayed() {
        activityScenario = ActivityScenario.launch(JobSearchActivity.class);

        onView(withId(R.id.job_type)).perform(click());
        onData(is("Food Delivery")).perform(click());
        onView(withId(R.id.job_type)).check(matches(withSpinnerText(containsString("Food Delivery"))));

        onView(withId(R.id.min_salary)).perform(click());
        onData(is("$15/hr")).perform(click());
        onView(withId(R.id.min_salary)).check(matches(withSpinnerText(containsString("$15/hr"))));

        // Repeat for max_distance spinner
        onView(withId(R.id.max_distance)).perform(click());
        onData(is("10km")).perform(click());
        onView(withId(R.id.max_distance)).check(matches(withSpinnerText(containsString("10km"))));
    }

}

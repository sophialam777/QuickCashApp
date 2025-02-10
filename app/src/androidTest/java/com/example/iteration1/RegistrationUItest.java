package com.example.iteration1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import static org.hamcrest.core.StringContains.containsString;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.example.iteration1.Registration;
import com.example.iteration1.MainActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegistrationUItest {

    @Rule
    public ActivityScenarioRule<Registration> activityRule = new ActivityScenarioRule<>(Registration.class);


    @Test
    public void testValidRegistration() {
        onView(withId(R.id.et_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("abc.123@dal.ca"), closeSoftKeyboard());
        onView(withId(R.id.et_contact)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("Pass123!@"), closeSoftKeyboard());
        onView(withId(R.id.spinner_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.btn_create_account)).perform(click());
        onView(withId(R.id.main)).check(matches(isDisplayed()));



    }

    @Test
    public void testEmptyFieldsShowError() {
        onView(withId(R.id.btn_create_account)).perform(click());
        onView(withId(R.id.error_message)).check(matches(withText("All input fields need to be filled")));
    }

    @Test
    public void testInvalidEmailShowsError() {
        onView(withId(R.id.et_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("invalidemail"), closeSoftKeyboard());
        onView(withId(R.id.et_contact)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("Pass123!@"), closeSoftKeyboard());
        onView(withId(R.id.spinner_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.btn_create_account)).perform(click());

        onView(withId(R.id.error_message)).check(matches(withText("Invalid email format")));
    }

    @Test
    public void testShortPasswordShowsError() {
        onView(withId(R.id.et_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("johndoe@example.com"), closeSoftKeyboard());
        onView(withId(R.id.et_contact)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("Pass1@"), closeSoftKeyboard());
        onView(withId(R.id.spinner_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.btn_create_account)).perform(click());

        onView(withId(R.id.error_message)).check(matches(withText("Password must be at least 8 characters")));
    }

    @Test
    public void testPasswordWithoutLowercaseShowsError() {
        onView(withId(R.id.et_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("johndoe@example.com"), closeSoftKeyboard());
        onView(withId(R.id.et_contact)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("PASSWORD1@"), closeSoftKeyboard());
        onView(withId(R.id.spinner_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.btn_create_account)).perform(click());

        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one lowercase letter")));
    }

    @Test
    public void testPasswordWithoutUppercaseShowsError() {
        onView(withId(R.id.et_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("johndoe@example.com"), closeSoftKeyboard());
        onView(withId(R.id.et_contact)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("password1@"), closeSoftKeyboard());
        onView(withId(R.id.spinner_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.btn_create_account)).perform(click());

        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one uppercase letter")));
    }

    @Test
    public void testPasswordWithoutDigitShowsError() {
        onView(withId(R.id.et_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("johndoe@example.com"), closeSoftKeyboard());
        onView(withId(R.id.et_contact)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("Password@"), closeSoftKeyboard());
        onView(withId(R.id.spinner_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.btn_create_account)).perform(click());

        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one digit")));
    }

    @Test
    public void testPasswordWithoutSymbolShowsError() {
        onView(withId(R.id.et_name)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("johndoe@example.com"), closeSoftKeyboard());
        onView(withId(R.id.et_contact)).perform(typeText("1234567890"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("Password1"), closeSoftKeyboard());
        onView(withId(R.id.spinner_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.btn_create_account)).perform(click());

        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one of the following characters: !, @, #, $, %, or &")));
    }
}

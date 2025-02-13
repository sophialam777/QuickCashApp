package com.example.iteration1;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


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
public class LoginUITest {

    @Rule
    public ActivityScenarioRule<Login> activityRule = new ActivityScenarioRule<>(Login.class);

    @Test
    public void testEmailRegistrationStatus() throws InterruptedException {
        final String testEmail = "mt769340@dal.ca";

        //Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://quickcash3130-4607d-default-rtdb.firebaseio.com/");
        DatabaseReference dbRef = database.getReference("users");

        //Query the database to check if the email exists
        dbRef.orderByChild("email").equalTo(testEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("Email found in database.");
                } else {
                    System.out.println("Email not found in database.");
                    //Ensuring this is run on the UI thread for Espresso actions
                    activityRule.getScenario().onActivity(activity -> {
                        onView(withId(R.id.error_message)).check(matches(withText("Email is not linked to an account")));
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.err.println("Database error: " + databaseError.getMessage());
            }
        });
    }

    //Test cases for email input validation
    @Test
    public void testEmptyEmailShowsError() {
        //Leave email empty
        onView(withId(R.id.login_password)).perform(typeText("Pass123!@"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the appropriate error message
        onView(withId(R.id.error_message)).check(matches(withText("All input fields need to be filled")));
    }

    @Test
    public void testInvalidEmailShowsError() {
        //Provide an invalid email format
        onView(withId(R.id.login_email)).perform(typeText("abc.123"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("Pass123!@"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the error message
        onView(withId(R.id.error_message)).check(matches(withText("Invalid email format")));
    }

    //Test cases for password validation
    @Test
    public void testEmptyPasswordShowsError() {
        //Leave password empty
        onView(withId(R.id.login_email)).perform(typeText("abc.123@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the error message regarding the empty password
        onView(withId(R.id.error_message)).check(matches(withText("All input fields need to be filled")));
    }

    @Test
    public void testShortPasswordShowsError() {
        //Provide a password that is too short
        onView(withId(R.id.login_email)).perform(typeText("abc.123@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("Short1@"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the password length error
        onView(withId(R.id.error_message)).check(matches(withText("Password must be at least 8 characters")));
    }

    @Test
    public void testPasswordWithoutLowercaseShowsError() {
        //Provide a password without a lowercase letter
        onView(withId(R.id.login_email)).perform(typeText("abc.123@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("PASSWORD123@"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the error message related to the lack of lowercase letters
        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one lowercase letter")));
    }

    @Test
    public void testPasswordWithoutUppercaseShowsError() {
        //Provide a password without an uppercase letter
        onView(withId(R.id.login_email)).perform(typeText("abc.123@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("password123@"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the error message related to the lack of uppercase letters
        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one uppercase letter")));
    }

    @Test
    public void testPasswordWithoutDigitShowsError() {
        // Provide a password without a digit
        onView(withId(R.id.login_email)).perform(typeText("abc.123@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("Password@"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the error message related to the lack of digits
        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one digit")));
    }

    @Test
    public void testPasswordWithoutSymbolShowsError() {
        //Provide a password without a special character
        onView(withId(R.id.login_email)).perform(typeText("abc.123@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("Password123"));
        closeSoftKeyboard();
        onView(withId(R.id.login_role)).perform(click());
        onView(withText("Employee")).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        //Check for the error message related to the lack of special characters
        onView(withId(R.id.error_message)).check(matches(withText("Password must contain at least one of the following characters: !, @, #, $, %, or &")));
    }

    @Test
    public void testRoleNotSelectedShowsError() {
        //Leave the role unselected
        onView(withId(R.id.login_email)).perform(typeText("abc.123@dal.ca"));
        closeSoftKeyboard();
        onView(withId(R.id.login_password)).perform(typeText("Pass123!@"));
        closeSoftKeyboard();
        onView(withId(R.id.login_button)).perform(click());

        //Check if an error is shown for the missing role
        onView(withId(R.id.error_message)).check(matches(withText("Please select a role")));
    }
}


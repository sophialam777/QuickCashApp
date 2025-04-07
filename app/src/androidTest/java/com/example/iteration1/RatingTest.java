package com.example.iteration1;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

import com.example.iteration1.validator.Job;
import com.sun.mail.imap.protocol.UID;

import java.util.ArrayList;

public class RatingTest {
    UiDevice device;

    @Before
    public void setUp() throws UiObjectNotFoundException {
        device = UiDevice.getInstance(getInstrumentation());
        try (ActivityScenario<Login> scenario = ActivityScenario.launch(Login.class)) {
            //Login before each test

            //Input email
            UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_email"));
            emailField.setText("beepboop@gmail.com");

            //Input password
            UiObject passwordField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_password"));
            passwordField.setText("Password1@#");

            //Open role spinner and select role
            UiObject roleSpinner = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_role"));
            roleSpinner.click();
            UiObject employeeOption = device.findObject(new UiSelector().text("Employer"));
            employeeOption.click();

            //Finish login
            UiObject loginButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_button"));
            loginButton.clickAndWaitForNewWindow();
        }
    }

    @Test
    public void ratingVisibilityTest() throws UiObjectNotFoundException {
        //Navigate to account info
        UiObject profileButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/edit_profile"));
        profileButton.clickAndWaitForNewWindow();

        //Check that the rating is visible on the profile
        UiObject rating = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/profile_rating"));
        assertTrue(rating.exists());
    }
}

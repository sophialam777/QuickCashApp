package com.example.iteration1;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.uiautomator.UiDevice.getInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class jobSearchTest {
    private UiDevice device;

    @Before
    public void setUp() throws Exception {
        // Get the device instance
        device = getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testSearch() throws UiObjectNotFoundException {
        //login with a existing account
        try (ActivityScenario<Login> scenario = ActivityScenario.launch(Login.class)) {
            //  enter the test email address
            UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_email"));
            emailField.setText("abc.1234@dal.ca"); //registered email
            //  enter the password of test email address
            UiObject passwordField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_password"));
            passwordField.setText("Pass123!@");
            //select the role
            UiObject role =  device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_role"));
            role.click();
            UiObject employeeRole = device.findObject(new UiSelector().text("Employee"));
            employeeRole.click();
            //click on login it
            UiObject loginButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_button"));
            loginButton.clickAndWaitForNewWindow();
            //moved to the main page and click on job search button
            UiObject jobSearchButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/jobsearch_button"));
            jobSearchButton.clickAndWaitForNewWindow();

            //select the location
            UiObject location =  device.findObject(new UiSelector().resourceId("com.example.iteration1:id/location"));
            location.click();
            UiObject chosenLocation = device.findObject(new UiSelector().text("North End Halifax"));
            chosenLocation.click();

            UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/search_button"));
            searchButton.clickAndWaitForNewWindow();

            // check if the user is moved back to the login page after hitting the logout button
            UiObject jobTitle = device.findObject(new UiSelector().text("Food Delivery Driver"));
            assertTrue(jobTitle.exists());
        }
    }

    @Test
    public void testSearch2() throws UiObjectNotFoundException {
        //login with a existing account
        try (ActivityScenario<Login> scenario = ActivityScenario.launch(Login.class)) {
            //  enter the test email address
            UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_email"));
            emailField.setText("abc.1234@dal.ca"); //registered email
            //  enter the password of test email address
            UiObject passwordField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_password"));
            passwordField.setText("Pass123!@");
            //select the role
            UiObject role =  device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_role"));
            role.click();
            UiObject employeeRole = device.findObject(new UiSelector().text("Employee"));
            employeeRole.click();
            //click on login it
            UiObject loginButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_button"));
            loginButton.clickAndWaitForNewWindow();
            //moved to the main page and click on job search button
            UiObject jobSearchButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/jobsearch_button"));
            jobSearchButton.clickAndWaitForNewWindow();

            //select the location
            UiObject location =  device.findObject(new UiSelector().resourceId("com.example.iteration1:id/min_salary"));
            location.click();
            UiObject chosenLocation = device.findObject(new UiSelector().text("$15/hr"));
            chosenLocation.click();

            UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/search_button"));
            searchButton.clickAndWaitForNewWindow();

            // check if the user is moved back to the login page after hitting the logout button
            UiObject jobTitle = device.findObject(new UiSelector().text("Software Engineer"));
            assertTrue(jobTitle.exists());
        }
    }

    @Test
    public void testViewMap() throws UiObjectNotFoundException {
        //login with a existing account
        try (ActivityScenario<Login> scenario = ActivityScenario.launch(Login.class)) {
            //  enter the test email address
            UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_email"));
            emailField.setText("abc.1234@dal.ca"); //registered email
            //  enter the password of test email address
            UiObject passwordField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_password"));
            passwordField.setText("Pass123!@");
            //select the role
            UiObject role =  device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_role"));
            role.click();
            UiObject employeeRole = device.findObject(new UiSelector().text("Employee"));
            employeeRole.click();
            //click on login it
            UiObject loginButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_button"));
            loginButton.clickAndWaitForNewWindow();
            //moved to the main page and click on job search button
            UiObject jobSearchButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/jobsearch_button"));
            jobSearchButton.clickAndWaitForNewWindow();

            //select the location
            UiObject location =  device.findObject(new UiSelector().resourceId("com.example.iteration1:id/location"));
            location.click();
            UiObject chosenLocation = device.findObject(new UiSelector().text("North End Halifax"));
            chosenLocation.click();
            //click on search button
            UiObject searchButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/search_button"));
            searchButton.clickAndWaitForNewWindow();
            //click on view map
            UiObject viewMapButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/view_map_button"));
            viewMapButton.clickAndWaitForNewWindow();

            // Check if markers for jobs are displayed
            UiObject foodDeliveryMarker = device.findObject(new UiSelector().descriptionContains("Food Delivery Driver"));
            assertTrue(foodDeliveryMarker.exists());
            foodDeliveryMarker.clickAndWaitForNewWindow();

            // Check if job details dialog is opened with the correct details
            UiObject jobDetailsDialog = device.findObject(new UiSelector().text("Job Details"));
            assertTrue(jobDetailsDialog.exists());
        }
    }

}

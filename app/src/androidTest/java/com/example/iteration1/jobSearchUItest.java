package com.example.iteration1;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;


public class jobSearchUItest {
    public String launcherPackage = "com.example.iteration1";
    public int TIME_OUT = 5000;
    UiDevice device;

    @Before
    public void setup() throws UiObjectNotFoundException {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        assert launcherIntent != null;
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


        // Send the job list with the Intent

        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIME_OUT);


        // Perform the login action before each test
        performLogin();
    }

    private void performLogin() throws UiObjectNotFoundException {
        // Input the email
        UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_email"));
        emailField.setText("mohammedzaksajan@gmail.com");

        // Input the password
        UiObject passwordField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_password"));
        passwordField.setText("Zakizaki1!");

        // Select the "Employee" role from the spinner
        UiObject roleSpinner = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_role"));
        roleSpinner.click(); // Open the spinner
        UiObject employeeRoleOption = device.findObject(new UiSelector().text("Employee")); // Adjust the role name if needed
        employeeRoleOption.click(); // Select "Employee"

        // Click on the "Log In" button
        UiObject loginButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_button"));
        loginButton.clickAndWaitForNewWindow();

        // Wait for the app to transition to the next screen
        UiObject jobSearch = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/jobsearch_button"));
        jobSearch.clickAndWaitForNewWindow();
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIME_OUT);
    }

    @Test
    public void testJobSearchActivity() throws Exception {
        UiObject pageTitle = device.findObject(new UiSelector().text("Job Search"));
        assertTrue(pageTitle .exists());
    }

    @Test
    public void testSearchResultActivity() throws Exception {
        UiObject searchButton= device.findObject(new UiSelector().resourceId("com.example.iteration1:id/search_button"));
        searchButton.clickAndWaitForNewWindow();
        UiObject pageTitle = device.findObject(new UiSelector().text("Search Result"));
        assertTrue(pageTitle.exists());
    }

    @Test
    public void testSearchResults() throws Exception {
        UiObject searchButton= device.findObject(new UiSelector().resourceId("com.example.iteration1:id/search_button"));
        searchButton.clickAndWaitForNewWindow();
        UiObject Results = device.findObject(new UiSelector().text("Food Delivery Driver"));
        assertTrue(Results.exists());
    }

    @Test
    public void testSearchDetailActivity() throws Exception {
        UiObject searchButton= device.findObject(new UiSelector().resourceId("com.example.iteration1:id/search_button"));
        searchButton.clickAndWaitForNewWindow();
        UiObject Results = device.findObject(new UiSelector().text("Food Delivery Driver"));
        Results.clickAndWaitForNewWindow();
        UiObject jobTitle = device.findObject(new UiSelector().text("Food Delivery Driver"));
        assertTrue(jobTitle.exists());
    }






}

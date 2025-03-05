package com.example.iteration1;

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

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.iteration1.validator.Job;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

public class MapUITest {

    public String launcherPackage = "com.example.iteration1"; // Modify with your app package
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

        UiObject jobposing = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/job_postings"));
        jobposing.clickAndWaitForNewWindow();
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIME_OUT);
    }



    @Test
    public void testGoogleMapsActivity() throws Exception {
        UiObject viewMapButton = device.findObject(new UiSelector().text("View Jobs on Map"));
        assertTrue(viewMapButton.exists());
        viewMapButton.clickAndWaitForNewWindow();
        // Check if Google Maps fragment is displayed
        UiObject mapView = device.findObject(new UiSelector().descriptionContains("Map"));
        assertTrue(mapView.exists());
    }

    @Test
    public void testJobsAreDisplayedOnMap() throws Exception {
        // Simulate adding markers for jobs (assuming they are already added in your app)
        UiObject viewMapButton = device.findObject(new UiSelector().text("View Jobs on Map"));
        assertTrue(viewMapButton.exists());
        viewMapButton.clickAndWaitForNewWindow();

        UiObject mapView = device.findObject(new UiSelector().descriptionContains("Map"));
        assertTrue(mapView.exists());

        // Check if markers for jobs are displayed
        UiObject foodDeliveryMarker = device.findObject(new UiSelector().descriptionContains("Food Delivery Driver"));
        assertTrue(foodDeliveryMarker.exists());

    }

    @Test
    public void testMarkerClickOpensJobDetailsDialog() throws Exception {
        UiObject viewMapButton = device.findObject(new UiSelector().text("View Jobs on Map"));
        assertTrue(viewMapButton.exists());
        viewMapButton.clickAndWaitForNewWindow();

        UiObject mapView = device.findObject(new UiSelector().descriptionContains("Map"));
        assertTrue(mapView.exists());

        // Check if markers for jobs are displayed
        UiObject foodDeliveryMarker = device.findObject(new UiSelector().descriptionContains("Food Delivery Driver"));
        assertTrue(foodDeliveryMarker.exists());
        foodDeliveryMarker.clickAndWaitForNewWindow();

        // Check if job details dialog is opened with the correct details
        UiObject jobDetailsDialog = device.findObject(new UiSelector().text("Job Details"));
        assertTrue(jobDetailsDialog.exists());

        UiObject jobTitle = device.findObject(new UiSelector().textContains("Title:"));
        UiObject jobDescription = device.findObject(new UiSelector().textContains("Description:"));


        assertTrue(jobTitle.exists());
        assertTrue(jobDescription.exists());

    }
}

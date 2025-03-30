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

import java.util.ArrayList;

public class ApplicationStatusTest {
    UiDevice device;

    @Before
    public void setUp() throws UiObjectNotFoundException {
        try (ActivityScenario<Login> scenario = ActivityScenario.launch(Login.class)) {
            //Log in as an employee before each test
            device = UiDevice.getInstance(getInstrumentation());

            //Enter email address
            UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_email"));
            emailField.setText("br490296@dal.ca");

            //Enter password
            UiObject passwordField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_password"));
            passwordField.setText("Password1@#");

            //Select 'Employee' role
            UiObject roleSpinner = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_role"));
            roleSpinner.click();
            UiObject employeeOption = device.findObject(new UiSelector().text("Employee"));
            employeeOption.click();

            //Submit login
            UiObject loginButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_button"));
            loginButton.clickAndWaitForNewWindow();
        }
    }

    @Test
    public void applicationStatusAccessibleFromDashboard() throws UiObjectNotFoundException {
        //Check if button to navigate to application status window exists
        UiObject applicationStatus = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/application_status_button"));
        applicationStatus.clickAndWaitForNewWindow();

        //Check if button properly leads to application status window
        UiObject applicationStatusView = device.findObject(new UiSelector().text("Active Applications"));
        assertTrue(applicationStatusView.exists());
    }

    @Test
    public void testAreApplicationStatusesVisible() {

    }
}

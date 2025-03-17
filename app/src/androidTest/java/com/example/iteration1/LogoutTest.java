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
public class LogoutTest {
    private UiDevice device;

    @Before
    public void setUp() throws Exception {
        // Get the device instance
        device = getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testLogout() throws UiObjectNotFoundException {
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
            //moved to the main page and click on logout button
            UiObject logoutButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_button"));
            logoutButton.clickAndWaitForNewWindow();
            // check if the user is moved back to the login page after hitting the logout button
            UiObject loginLabel = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/login_text"));
            assertTrue(loginLabel.exists());
        }
    }

}

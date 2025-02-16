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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.uiautomator.UiDevice.getInstance;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ForgotPasswordTest {

    private UiDevice device;
    private Context context;

    @Before
    public void setUp() throws Exception {
        // Get the device instance
        device = getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testEmailNotRegisteredError() throws UiObjectNotFoundException {
        // Start ForgotPassword Activity using the ActivityScenario API
        try (ActivityScenario<ForgotPassword> scenario = ActivityScenario.launch(ForgotPassword.class)) {

            // Find email input field and enter an unregistered email address
            UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/etEmail"));
            emailField.setText("unregisteredemail@dal.ca");

            // Find the send button and click it
            UiObject sendButton = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/btnSendCode"));
            sendButton.click();

            // Wait for the error message to appear
            UiObject errorMessage = device.findObject(new UiSelector().resourceId("com.example.iteration1:id/tvErrorMessage"));
            assertTrue("Error message should be displayed for an unregistered email", errorMessage.exists());

            // Check if the error message matches the expected text
            String expectedMessage = "Please enter a valid email address.";
            String actualMessage = errorMessage.getText();
            assertTrue("The error message is incorrect. Expected: " + expectedMessage + ", but got: " + actualMessage,
                    actualMessage.equals(expectedMessage));
        }
    }
}
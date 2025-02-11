package com.example.iteration1;

import static org.robolectric.Shadows.shadowOf;

import android.os.Looper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.*;

import com.example.iteration1.validator.RegistrationValidator;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RegistrationUnitTest {
    private RegistrationValidator validator;

    @Before
    public void setup() {
        validator = new RegistrationValidator();
    }

    @Test
    public void checkIfNameIsEmpty() {
        assertTrue(validator.isEmptyName(""));
    }

    @Test
    public void checkIfEmailIsValid() {
        assertTrue(validator.isValidEmailAddress("abc123@dal.ca"));
    }

    @Test
    public void checkIfEmailIsNotValid() {
        assertFalse(validator.isValidEmailAddress("abc.123dal.ca"));
    }

    @Test
    public void checkIfContactIsValid() {
        assertTrue(validator.isValidContact("1234567890"));
    }

    @Test
    public void checkIfContactIsNotValid() {
        assertFalse(validator.isValidContact("12345"));
    }

    @Test
    public void checkIfRoleIsValid() {
        assertTrue(validator.isValidRole("Employee"));
        assertTrue(validator.isValidRole("Employer"));
    }

    @Test
    public void checkIfRoleIsNotValid() {
        assertFalse(validator.isValidRole("Select Role"));
    }

    @Test
    public void checkIfPasswordIsValid() {
        String password = "Password1@";
        assertTrue(validator.isPasswordLongEnough(password));
        assertTrue(validator.isLowercaseInPassword(password));
        assertTrue(validator.isUppercaseInPassword(password));
        assertTrue(validator.isDigitInPassword(password));
        assertTrue(validator.isSymbolInPassword(password));
    }

    @Test
    public void checkIfPasswordIsTooShort() {
        assertFalse(validator.isPasswordLongEnough("Pass1@"));
    }

    @Test
    public void checkIfPasswordNeedsLowercase() {
        assertFalse(validator.isLowercaseInPassword("PASSWORD1@"));
    }

    @Test
    public void checkIfPasswordNeedsUppercase() {
        assertFalse(validator.isUppercaseInPassword("password1@"));
    }

    @Test
    public void checkIfPasswordNeedsDigit() {
        assertFalse(validator.isDigitInPassword("Password@"));
    }

    @Test
    public void checkIfPasswordNeedsSymbol() {
        assertFalse(validator.isSymbolInPassword("Password1"));
    }

    // New test case for verifying that a confirmation email is sent upon successful registration.
    @Test
    public void testConfirmationEmailSent() {
        // Set test mode so that Email.sendConfirmationEmail simulates success.
        Email.isTest = true;
        // Reset the test flag before sending the email.
        Email.testEmailSent = false;
        // Trigger sending the confirmation email with dummy data.
        Email.sendConfirmationEmail("test@example.com", "TestUser");
        // Flush the main looper so that any pending tasks complete.
        shadowOf(Looper.getMainLooper()).idle();
        // Verify that the email was "sent" successfully.
        assertTrue("Confirmation email should be sent successfully", Email.testEmailSent);
    }
}

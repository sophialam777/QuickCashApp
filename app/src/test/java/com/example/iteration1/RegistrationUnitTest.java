package com.example.iteration1;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.iteration1.validator.RegistrationValidator;

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
}


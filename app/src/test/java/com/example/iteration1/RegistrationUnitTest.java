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
        assertTrue(validator.isValidEmailAddress("abc123@dal.ca"));;
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
    public void checkIfPasswordIsValid() {
        assertTrue(validator.isValidPassword("password123"));
    }

    @Test
    public void checkIfPasswordIsNotValid() {
        assertFalse(validator.isValidPassword("pass"));
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
}


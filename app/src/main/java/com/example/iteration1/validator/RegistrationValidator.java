package com.example.iteration1.validator;


import android.util.Patterns;

public class RegistrationValidator {

    public boolean isEmptyName(String name) {
        return name == null || name.trim().isEmpty();
    }

    public boolean isEmptyEmailAddress(String emailAddress) {
        return emailAddress == null || emailAddress.trim().isEmpty();
    }

    public boolean isValidEmailAddress(String emailAddress) {
        return emailAddress != null && emailAddress.contains("@") && emailAddress.contains(".");
    }

    public boolean isValidContact(String contact) {
        return contact != null && contact.length() >= 10;
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    public boolean isValidRole(String role) {
        return role != null && !role.equals("Select Role");
    }
}

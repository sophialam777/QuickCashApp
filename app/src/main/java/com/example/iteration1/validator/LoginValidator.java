package com.example.iteration1.validator;

import android.util.Patterns;
import com.example.iteration1.userAccount;

public class LoginValidator {

    //Method to check if the email is valid
    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Method to check if the password is valid
    public boolean isPasswordValid(String password) {
        return password.length() >= 8 &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[!@#$%&].*");
    }

    //Method to check if the login is successful
    public boolean isLoginSuccessful(String email, String password) {
        userAccount user = getUserFromDatabase(email);

        if (user == null) {
            return false;  //If user doesn't exist
        }

        //Check if the email and password match the user data
        return user.getEmail().equals(email) && user.getPassword().equals(password);
    }

    public boolean doesUserExist(String email) {
        return "test@example.com".equals(email);
    }

    private userAccount getUserFromDatabase(String email) {
        if ("mt769340@dal.ca".equals(email)) {
            return new userAccount("matthew2", "mt769340@dal.ca", "passworD123!", "111122223333", "Employee", "-OIv6AUXoxT4TOWM7o-y");
        } else if ("test@example.com".equals(email)) {
            return new userAccount("Zaki", "mh123123@dal.ca", "Zakizaki1!", "1234567890", "Employee", "-OIviKuWWouFNGjim-V9");
        }
        return null;
    }
}

package com.example.iteration1;

public class UserSession {
    static String name, email, contact, role, userID;
    static boolean loggedIn;

    public UserSession(String name , String email , String contact, String role, String userID){
        UserSession.name = name ;
        UserSession.email = email ;
        UserSession.contact = contact;
        UserSession.role = role;
        UserSession.userID = userID;
        loggedIn = true;
    }

    public static void logout(){
        UserSession.name = "";
        UserSession.email = "";
        UserSession.contact = "";
        UserSession.role = "";
        UserSession.userID = "";
        loggedIn = false;
    }
}

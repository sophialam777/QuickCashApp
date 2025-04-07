package com.example.iteration1;

public class UserSession {
    static String name, email, contact, role, userID, fcmToken;
    static boolean loggedIn;

    public UserSession(){}

    public static void logout(){
        UserSession.name = "";
        UserSession.email = "";
        UserSession.contact = "";
        UserSession.role = "";
        UserSession.userID = "";
        UserSession.fcmToken = "";
        loggedIn = false;
    }

    public static void login(String name , String email , String contact, String role, String userID, String fcmToken){
        UserSession.name = name ;
        UserSession.email = email ;
        UserSession.contact = contact;
        UserSession.role = role;
        UserSession.userID = userID;
        UserSession.fcmToken = fcmToken;
        loggedIn = true;
    }
}
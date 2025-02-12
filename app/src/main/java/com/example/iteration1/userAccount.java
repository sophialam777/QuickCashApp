package com.example.iteration1;

public class userAccount {
    String name, email, password, contact, role, userID;

    public userAccount(){}

    public userAccount(String name , String email , String password , String contact, String role, String userID){
        this.name = name ;
        this.email = email ;
        this.password = password ;
        this.contact = contact;
        this.role = role;
        this.userID = userID;
    }

    public String getName(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){return this.password;}
    public String getContact(){
        return this.contact;
    }
    public String getRole(){
        return this.role;
    }
    public String getUserID(){
        return this.userID;
    }



}

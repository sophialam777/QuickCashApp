package com.example.iteration1;

public class userAccount {
    String name;
    String email ;
    String password;
    String contact;
    String role;


    public userAccount(String name , String email , String password , String contact, String role){
        this.name = name ;
        this.email = email ;
        this.password = password ;
        this.contact = contact;
        this.role = role;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getContact(){
        return this.contact;
    }
    public String getRole(){
        return this.role;
    }


}

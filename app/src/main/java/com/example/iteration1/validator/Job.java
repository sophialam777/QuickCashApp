package com.example.iteration1.validator;

import java.io.Serializable;
import java.util.List;

public class Job implements Serializable {
    private String title, location, description, type, pay, posterName, posterEmail;
    private double latitude,longitude;
    private List<String> questions;

    public Job(String title, String location, String description, String type, String pay,
               double latitude, double longitude, List<String> questions, String posterEmail, String posterName) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.type = type;
        this.pay = pay;
        this.latitude = latitude;
        this.longitude = longitude;
        this.questions = questions;
        this.posterEmail = posterEmail;
        this.posterName = posterName;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getPay() {
        return pay;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getPosterEmail() {
        return posterEmail;
    }

    @Override
    public String toString() {
        return title;
    }
}
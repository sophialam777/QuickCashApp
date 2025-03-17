package com.example.iteration1.validator;

import java.io.Serializable;
import java.util.List;

public class Job implements Serializable {
    private String title;
    private String location;
    private String description;
    private String type;
    private String pay;
    private double latitude;
    private double longitude;
    private List<String> questions;

    public Job(String title, String location, String description, String type, String pay,
               double latitude, double longitude, List<String> questions) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.type = type;
        this.pay = pay;
        this.latitude = latitude;
        this.longitude = longitude;
        this.questions = questions;
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

    @Override
    public String toString() {
        return title;
    }
}

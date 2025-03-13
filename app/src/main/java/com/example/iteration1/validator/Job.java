package com.example.iteration1.validator;

import java.io.Serializable;
import java.util.List;

public class Job implements Serializable {
    private String title;
    private String description;
    private String location;
    private String type;
    private String pay;
    private double latitude;
    private double longitude;
    private List<String> questions;

    public Job(String title, String location, String description, String type, String pay, double latitude, double longitude, List<String> questions) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return title;
    }
}
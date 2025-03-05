package com.example.iteration1.validator;

import java.io.Serializable;

public class Job implements Serializable {
    private String title;
    private String description;
    private String requirements;
    private String instructions;
    private double latitude;
    private double longitude;

    public Job(String title, String description, String requirements, String instructions, double latitude, double longitude) {
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.instructions = instructions;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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

    @Override
    public String toString() {
        return title;
    }
}
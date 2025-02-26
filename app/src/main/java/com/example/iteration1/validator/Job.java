package com.example.iteration1.validator;

import java.io.Serializable;

public class Job implements Serializable {
    private String title;
    private String description;
    private String requirements;
    private String instructions;

    public Job(String title, String description, String requirements, String instructions) {
        this.title = title;
        this.description = description;
        this.requirements = requirements;
        this.instructions = instructions;
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

    @Override
    public String toString() {
        return title;
    }
}

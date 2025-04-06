package com.example.iteration1;

import java.io.Serializable;

public class JobOffer implements Serializable {

    private String salary, startDate, location, status, other, applicationID;

    public JobOffer(){}

    public JobOffer(String salary, String startDate, String location, String other, String status, String applicationID){
        this.salary = salary;
        this.startDate = startDate;
        this.location = location;
        this.status = status;
        this.other = other;
        this.applicationID = applicationID;
    }

    public String getSalary(){ return salary; }
    public String getStartDate(){ return startDate; }
    public String getLocation(){ return location; }
    public String getStatus(){ return status; }
    public String getOther(){ return other; }
    public String getApplicationID(){ return applicationID; }

    public void setStatus(String status){ this.status = status; }
}
package com.example.iteration1;

import java.util.ArrayList;
import java.util.List;

public class Employer {
    private List<Employee> preferredEmployees;

    public Employer() {
        this.preferredEmployees = new ArrayList<>();
    }

    public void addToPreferred(Employee employee) {
        if (!preferredEmployees.contains(employee)) {
            preferredEmployees.add(employee);
        }
    }

    public void removeFromPreferred(Employee employee) {
        preferredEmployees.remove(employee);
    }

    public List<Employee> getPreferredEmployees() {
        return preferredEmployees;
    }
}

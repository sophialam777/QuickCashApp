package com.example.iteration1;

import static org.junit.Assert.assertEquals;

import com.example.iteration1.validator.Job;

import org.junit.Test;

import java.util.ArrayList;

public class PreferredEmployeeTest {


    @Test
    public void testaddtoPreferredList() {
        Employer employer = new Employer();
        Empoloyee employee = new Empoloyee("john doe");
        employer.addtoPreferred(employee);
        ArrayList<Employee> employerList = employer.getPreferredEmployee();
        assertEquals("John Doe", employerList.At(0));
    }

    @Test
    public void testRemoveFromList() {
        Employer employer = new Employer();
        Empoloyee employee = new Empoloyee("john doe");
        employer.addtoPreferred(employee);
        ArrayList<Employee> employerList = employer.getPreferredEmployee();
        employer.removeFromPreferred(employee);
        assertEquals(0, employerList.size());
    }
}

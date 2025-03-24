package com.example.iteration1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.List;

public class PreferredEmployeeTest {

    @Test
    public void testAddToPreferredList() {
        Employer employer = new Employer();
        Employee employee = new Employee("John Doe");

        employer.addToPreferred(employee);
        List<Employee> employerList = employer.getPreferredEmployees();

        assertEquals(1, employerList.size());
        assertEquals("John Doe", employerList.get(0).getName());
    }

    @Test
    public void testRemoveFromPreferredList() {
        Employer employer = new Employer();
        Employee employee = new Employee("John Doe");

        employer.addToPreferred(employee);
        employer.removeFromPreferred(employee);
        List<Employee> employerList = employer.getPreferredEmployees();

        assertEquals(0, employerList.size());
    }
}

package com.example.iteration1;

import static org.junit.Assert.assertEquals;

import com.example.iteration1.validator.Job;

import org.junit.Test;

public class JobListDetailsTest {
    @Test
    public void testJobGetters() {
        Job job = new Job("Software Engineer", "Develop software", "Java, Kotlin", "Submit resume");

        assertEquals("Software Engineer", job.getTitle());
        assertEquals("Develop software", job.getDescription());
        assertEquals("Java, Kotlin", job.getRequirements());
        assertEquals("Submit resume", job.getInstructions());
    }
}

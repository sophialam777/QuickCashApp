package com.example.iteration1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.iteration1.validator.Job;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JobListDetailsTest {

    @Test
    public void testJobGetters() {
        // Prepare a list of questions
        List<String> questions = Arrays.asList("Do you have a driver's license?", "Are you available weekends?");

        // Use the new constructor
        Job job = new Job(
                "Software Engineer",  // title
                "Halifax",            // location
                "Develop software",   // description
                "Full-time",          // type
                "$30/hour",           // pay
                44.6452,              // latitude
                -63.5736,             // longitude
                questions
        );

        double delta = 0.0001;

        // Validate new fields
        assertEquals("Software Engineer", job.getTitle());
        assertEquals("Halifax", job.getLocation());
        assertEquals("Develop software", job.getDescription());
        assertEquals("Full-time", job.getType());
        assertEquals("$30/hour", job.getPay());
        assertEquals(44.6452, job.getLatitude(), delta);
        assertEquals(-63.5736, job.getLongitude(), delta);

        // Validate questions
        assertNotNull(job.getQuestions());
        assertEquals(2, job.getQuestions().size());
        assertEquals("Do you have a driver's license?", job.getQuestions().get(0));
        assertEquals("Are you available weekends?", job.getQuestions().get(1));
    }
}

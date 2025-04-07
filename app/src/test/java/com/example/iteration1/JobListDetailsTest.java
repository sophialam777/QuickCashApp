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
                "Freelance Writer",
                "Halifax",
                "Write content for blogs",
                "Part-time",
                "$25/article",
                44.6413,
                -63.5887,
                Arrays.asList("Do you have writing samples?"),
                "xyz987@dal.ca",
                "m"
        );

        double delta = 0.0001;

        // Validate new fields
        assertEquals("Freelance Writer", job.getTitle());
        assertEquals("Halifax", job.getLocation());
        assertEquals("Write content for blogs", job.getDescription());
        assertEquals("Part-time", job.getType());
        assertEquals("$25/article", job.getPay());
        assertEquals(44.6413, job.getLatitude(), delta);
        assertEquals(-63.5887, job.getLongitude(), delta);

        // Validate questions
        assertNotNull(job.getQuestions());
        assertEquals(1, job.getQuestions().size());
        assertEquals("Do you have writing samples?", job.getQuestions().get(0));
    }
}

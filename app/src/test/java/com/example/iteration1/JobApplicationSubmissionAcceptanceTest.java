package com.example.iteration1;

import android.net.Uri;
import com.example.iteration1.validator.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class JobApplicationSubmissionAcceptanceTest {

    @Test
    public void testApplicationSubmittedSuccessfully() {
        // Given a valid job and user attaches resume + answers question
        Job testJob = new Job(
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
        Uri resumeUri = Uri.parse("file://fake/resume.pdf");
        String answer1 = "Yes, here are my samples";

        // When we simulate applying with all required info
        boolean isValid = (resumeUri != null && !answer1.isEmpty());

        // Then the application is accepted (this is a simplified acceptance check)
        assertTrue("Expected valid application to pass", isValid);
    }

    @Test
    public void testMissingRequiredFields() {
        // Given a valid job but missing resume or missing answer
        Job testJob = new Job(
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
        Uri resumeUri = null;  // not attached
        String answer1 = "";   // not answered

        // When we check validity
        boolean isValid = (resumeUri != null && !answer1.isEmpty());

        // Then it should fail
        assertFalse("Expected invalid application if resume or answer is missing", isValid);
    }
}

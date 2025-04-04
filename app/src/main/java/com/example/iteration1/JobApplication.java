package com.example.iteration1;

public class JobApplication {
    private String applicationId;
    private String jobTitle;
    private String applicantEmail;
    private String resumeUri;
    private String answer1;
    private String answer2;
    private String status;

    // Required no-argument constructor for Firebase
    public JobApplication() {}

    public JobApplication(String applicationId, String jobTitle, String applicantEmail, String resumeUri, String answer1, String answer2, String status) {
        this.applicationId = applicationId;
        this.jobTitle = jobTitle;
        this.applicantEmail = applicantEmail;
        this.resumeUri = resumeUri;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.status = status;
    }

    // Getters and setters
    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getApplicantEmail() { return applicantEmail; }
    public void setApplicantEmail(String applicantEmail) { this.applicantEmail = applicantEmail; }

    public String getResumeUri() { return resumeUri; }
    public void setResumeUri(String resumeUri) { this.resumeUri = resumeUri; }

    public String getAnswer1() { return answer1; }
    public void setAnswer1(String answer1) { this.answer1 = answer1; }

    public String getAnswer2() { return answer2; }
    public void setAnswer2(String answer2) { this.answer2 = answer2; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

package com.example.iteration1;

public class Rating {
    private String ratingId;
    private String ratedUserEmail;
    private String raterUserEmail;
    private float ratingValue;
    private String feedback;

    // No-argument constructor for Firebase
    public Rating() {}

    public Rating(String ratingId, String ratedUserEmail, String raterUserEmail, float ratingValue, String feedback) {
        this.ratingId = ratingId;
        this.ratedUserEmail = ratedUserEmail;
        this.raterUserEmail = raterUserEmail;
        this.ratingValue = ratingValue;
        this.feedback = feedback;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getRatedUserEmail() {
        return ratedUserEmail;
    }

    public void setRatedUserEmail(String ratedUserEmail) {
        this.ratedUserEmail = ratedUserEmail;
    }

    public String getRaterUserEmail() {
        return raterUserEmail;
    }

    public void setRaterUserEmail(String raterUserEmail) {
        this.raterUserEmail = raterUserEmail;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

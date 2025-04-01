package com.example.iteration1.notfication;

public interface AccessTokenListener {
    void onAccessTokenReceived(String token);
    void onAccessTokenError(Exception exception);
}


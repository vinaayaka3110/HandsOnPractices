package com.mockito.example;

public interface ExternalApi {
    String getData();
    String fetchUserData(String userId);
    boolean isServiceAvailable();
    int getStatusCode();
}

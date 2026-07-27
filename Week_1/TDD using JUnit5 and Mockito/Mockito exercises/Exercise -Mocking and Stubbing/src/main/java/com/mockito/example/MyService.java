package com.mockito.example;

public class MyService {
    private ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return externalApi.getData();
    }

    public String getUserInfo(String userId) {
        if (externalApi.isServiceAvailable()) {
            return externalApi.fetchUserData(userId);
        }
        return "Service unavailable";
    }

    public String getServiceStatus() {
        int statusCode = externalApi.getStatusCode();
        if (statusCode == 200) {
            return "Service is healthy";
        } else if (statusCode >= 400 && statusCode < 500) {
            return "Client error";
        } else if (statusCode >= 500) {
            return "Server error";
        } else {
            return "Unknown status";
        }
    }
}

package com.mockito.verification;

public class MyService {
    private ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return externalApi.getData();
    }

    public String processAndSave(String input) {
        String processed = externalApi.processData(input);
        externalApi.saveData(processed);
        return processed;
    }

    public boolean removeUserData(String userId) {
        return externalApi.deleteData(userId);
    }

    public String getUserProfile(String userId) {
        return externalApi.fetchUserData(userId, "profile");
    }

    public String getUserSettings(String userId) {
        return externalApi.fetchUserData(userId, "settings");
    }

    public void configureSystem(String environment) {
        externalApi.updateSettings("environment", environment);
        externalApi.updateSettings("mode", "production");
    }

    public int performCalculation(int x, int y) {
        return externalApi.calculateSum(x, y);
    }

    public void multipleOperations() {
        externalApi.getData();
        externalApi.getData();
        externalApi.processData("test");
    }
}

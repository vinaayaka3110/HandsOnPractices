package com.mockito.verification;

public interface ExternalApi {
    String getData();
    String processData(String input);
    void saveData(String data);
    boolean deleteData(String id);
    String fetchUserData(String userId, String type);
    void updateSettings(String key, String value);
    int calculateSum(int a, int b);
}

package com.mockito.example;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MyServiceTest {

    @Mock
    private ExternalApi mockApi;
    
    private MyService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new MyService(mockApi);
    }

    @Test
    public void testExternalApi() {
        // Arrange - Stub the mock to return predefined data
        when(mockApi.getData()).thenReturn("Mock Data");
        
        // Act - Call the service method
        String result = service.fetchData();
        
        // Assert - Verify the result
        assertEquals("Mock Data", result);
        
        // Verify that the mock was called
        verify(mockApi).getData();
    }

    @Test
    public void testGetUserInfoWhenServiceAvailable() {
        // Arrange - Multiple stubbing
        when(mockApi.isServiceAvailable()).thenReturn(true);
        when(mockApi.fetchUserData("user123")).thenReturn("User Data for user123");
        
        // Act
        String result = service.getUserInfo("user123");
        
        // Assert
        assertEquals("User Data for user123", result);
        
        // Verify interactions
        verify(mockApi).isServiceAvailable();
        verify(mockApi).fetchUserData("user123");
    }

    @Test
    public void testGetUserInfoWhenServiceUnavailable() {
        // Arrange - Stub to return false
        when(mockApi.isServiceAvailable()).thenReturn(false);
        
        // Act
        String result = service.getUserInfo("user123");
        
        // Assert
        assertEquals("Service unavailable", result);
        
        // Verify that isServiceAvailable was called but fetchUserData was not
        verify(mockApi).isServiceAvailable();
        verify(mockApi, never()).fetchUserData(anyString());
    }

    @Test
    public void testGetServiceStatusHealthy() {
        // Arrange - Stub status code
        when(mockApi.getStatusCode()).thenReturn(200);
        
        // Act
        String result = service.getServiceStatus();
        
        // Assert
        assertEquals("Service is healthy", result);
        verify(mockApi).getStatusCode();
    }

    @Test
    public void testGetServiceStatusClientError() {
        // Arrange
        when(mockApi.getStatusCode()).thenReturn(404);
        
        // Act
        String result = service.getServiceStatus();
        
        // Assert
        assertEquals("Client error", result);
    }

    @Test
    public void testGetServiceStatusServerError() {
        // Arrange
        when(mockApi.getStatusCode()).thenReturn(500);
        
        // Act
        String result = service.getServiceStatus();
        
        // Assert
        assertEquals("Server error", result);
    }

    @Test
    public void testMockingWithoutAnnotations() {
        // Alternative way to create mocks without annotations
        ExternalApi mockApiLocal = mock(ExternalApi.class);
        when(mockApiLocal.getData()).thenReturn("Local Mock Data");
        
        MyService localService = new MyService(mockApiLocal);
        String result = localService.fetchData();
        
        assertEquals("Local Mock Data", result);
        verify(mockApiLocal, times(1)).getData();
    }

    @Test
    public void testVerifyNoMoreInteractions() {
        // Arrange
        when(mockApi.getData()).thenReturn("Test Data");
        
        // Act
        service.fetchData();
        
        // Assert and verify
        verify(mockApi).getData();
        verifyNoMoreInteractions(mockApi);
    }
}

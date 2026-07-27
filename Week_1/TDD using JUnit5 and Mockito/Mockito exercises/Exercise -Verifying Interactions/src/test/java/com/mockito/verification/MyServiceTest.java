
package com.mockito.verification;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

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
    public void testVerifyInteraction() {
        // Arrange
        when(mockApi.getData()).thenReturn("Mock Data");
        
        // Act
        service.fetchData();
        
        // Assert - Verify that getData() was called exactly once
        verify(mockApi).getData();
    }

    @Test
    public void testVerifyInteractionWithSpecificArguments() {
        // Arrange
        when(mockApi.fetchUserData("user123", "profile")).thenReturn("User Profile");
        
        // Act
        service.getUserProfile("user123");
        
        // Assert - Verify method called with specific arguments
        verify(mockApi).fetchUserData("user123", "profile");
    }

    @Test
    public void testVerifyMultipleInteractions() {
        // Arrange
        when(mockApi.processData("input")).thenReturn("processed");
        
        // Act
        service.processAndSave("input");
        
        // Assert - Verify multiple method calls
        verify(mockApi).processData("input");
        verify(mockApi).saveData("processed");
    }

    @Test
    public void testVerifyNumberOfInteractions() {
        // Act
        service.multipleOperations();
        
        // Assert - Verify getData was called exactly 2 times
        verify(mockApi, times(2)).getData();
        // Verify processData was called exactly once
        verify(mockApi, times(1)).processData("test");
    }

    @Test
    public void testVerifyNeverCalled() {
        // Act - Only call fetchData, not processData
        service.fetchData();
        
        // Assert - Verify processData was never called
        verify(mockApi).getData();
        verify(mockApi, never()).processData(anyString());
        verify(mockApi, never()).saveData(anyString());
    }

    @Test
    public void testVerifyAtLeastOnce() {
        // Act
        service.multipleOperations();
        
        // Assert - Verify getData was called at least once
        verify(mockApi, atLeastOnce()).getData();
        verify(mockApi, atLeast(1)).processData("test");
    }

    @Test
    public void testVerifyAtMost() {
        // Act
        service.fetchData();
        
        // Assert - Verify getData was called at most once
        verify(mockApi, atMost(1)).getData();
    }

    @Test
    public void testVerifyWithArgumentMatchers() {
        // Act
        service.getUserProfile("anyUser");
        service.getUserSettings("anyUser");
        
        // Assert - Verify calls with argument matchers
        verify(mockApi).fetchUserData(eq("anyUser"), eq("profile"));
        verify(mockApi).fetchUserData(eq("anyUser"), eq("settings"));
        
        // Verify any string arguments
        verify(mockApi, times(2)).fetchUserData(anyString(), anyString());
    }

    @Test
    public void testVerifyInOrder() {
        // Arrange
        when(mockApi.processData("data")).thenReturn("processed");
        
        // Act
        service.processAndSave("data");
        
        // Assert - Verify calls happened in specific order
        InOrder inOrder = inOrder(mockApi);
        inOrder.verify(mockApi).processData("data");
        inOrder.verify(mockApi).saveData("processed");
    }

    @Test
    public void testArgumentCaptor() {
        // Arrange
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        
        // Act
        service.configureSystem("development");
        
        // Assert - Capture and verify arguments
        verify(mockApi, times(2)).updateSettings(keyCaptor.capture(), valueCaptor.capture());
        
        assertEquals("environment", keyCaptor.getAllValues().get(0));
        assertEquals("development", valueCaptor.getAllValues().get(0));
        assertEquals("mode", keyCaptor.getAllValues().get(1));
        assertEquals("production", valueCaptor.getAllValues().get(1));
    }

    @Test
    public void testVerifyWithTimeout() {
        // Act
        service.fetchData();
        
        // Assert - Verify with timeout (useful for async operations)
        verify(mockApi, timeout(1000)).getData();
    }

    @Test
    public void testVerifyNoMoreInteractions() {
        // Act
        service.fetchData();
        
        // Assert - Verify only expected interactions occurred
        verify(mockApi).getData();
        verifyNoMoreInteractions(mockApi);
    }

    @Test
    public void testVerifyZeroInteractions() {
        // Act - Don't call any service methods
        
        // Assert - Verify no interactions with mock
        verifyNoInteractions(mockApi);
    }

    @Test
    public void testVerifyWithCustomMatcher() {
        // Act
        service.performCalculation(5, 3);
        
        // Assert - Verify with argument matchers
        verify(mockApi).calculateSum(eq(5), eq(3));
        verify(mockApi).calculateSum(anyInt(), anyInt());
    }
}

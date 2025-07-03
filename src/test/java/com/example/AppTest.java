package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    
    @Test
    public void testApplicationRuns() {
        // Simple test to verify the application can run
        String javaVersion = System.getProperty("java.version");
        assertNotNull("Java version should not be null", javaVersion);
        assertTrue("Should be Java 1.8", javaVersion.startsWith("1.8"));
    }
    
    @Test
    public void testStringOperations() {
        String test = "Hello World";
        assertNotNull(test);
        assertEquals(11, test.length());
    }
}
package com.example;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Java 1.8 Maven Docker Application ===");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Home: " + System.getProperty("java.home"));
        System.out.println("User: " + System.getProperty("user.name"));
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        
        // Test Apache Commons Lang
        String message = "Hello from Docker Container!";
        System.out.println("Original: " + message);
        System.out.println("Reversed: " + StringUtils.reverse(message));
        
        // Test Jackson JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            data.put("application", "Java Maven Docker");
            data.put("version", "1.0-SNAPSHOT");
            data.put("java_version", System.getProperty("java.version"));
            data.put("timestamp", System.currentTimeMillis());
            
            String json = mapper.writeValueAsString(data);
            System.out.println("JSON Output: " + json);
        } catch (Exception e) {
            System.err.println("Error creating JSON: " + e.getMessage());
        }
        
        // Call our new test method
        testMethod();
        
        System.out.println("=== Application Started Successfully ===");
    }

    public static void testMethod() {
        System.out.println("Testing IntelliSense");
    }


}
package com.example;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        
        // HTTP Request functionality
        executeHttpRequestDemo();
        
        System.out.println("=== Application Started Successfully ===");
    }
    
    /**
     * Demonstrates HTTP GET request functionality
     */
    public static void executeHttpRequestDemo() {
        System.out.println("\n=== HTTP Request Demo ===");
        HttpRequestClient httpClient = new HttpRequestClient();
        
        try {
            System.out.println("Using properties file for HTTP request configuration");
            String response = httpClient.sendGetRequestWithPropertiesFile("http-config.properties");
            
            System.out.println("\n=== HTTP Response ===");
            System.out.println(response);
            
            // Parse comma-separated response
            if (response.length() > 0 && response.contains(",")) {
                String strResponse = response;
                String[] cpaResponse = strResponse.split(",");

                String msisdn = null, msgID = null, statusCode = null;

                if (cpaResponse.length > 2) {
                    msisdn = cpaResponse[0];
                    msgID = cpaResponse[1];
                    statusCode = cpaResponse[2];
                    
                    System.out.println("\n=== Parsed Response ===");
                    System.out.println("MSISDN: " + msisdn);
                    System.out.println("Message ID: " + msgID);
                    System.out.println("Status Code: " + statusCode);
                }
            }
        } catch (Exception e) {
            System.err.println("Error executing HTTP request: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
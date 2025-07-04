package com.example;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class HttpRequestClientTest {
    
    private static final String TEST_PROPERTIES_FILE = "test-http-config.properties";
    private HttpRequestClient httpClient;
    
    @Before
    public void setUp() {
        httpClient = new HttpRequestClient();
        createTestPropertiesFile();
    }
    
    @Test
    public void testSendGetRequestWithPropertiesFile() {
        try {
            String response = httpClient.sendGetRequestWithPropertiesFile(TEST_PROPERTIES_FILE);
            
            // Verify response is not null or empty
            assertNotNull("Response should not be null", response);
            assertFalse("Response should not be empty", response.isEmpty());
            
            // Verify response contains our test parameters
            assertTrue("Response should contain test parameter", 
                    response.contains("testParam1") && response.contains("testValue1"));
            
            System.out.println("HTTP Response: " + response);
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
    
    private void createTestPropertiesFile() {
        try {
            Properties props = new Properties();
            props.setProperty("http.url", "https://httpbin.org/get");
            props.setProperty("http.param.name1", "testParam1");
            props.setProperty("http.param.value1", "testValue1");
            props.setProperty("http.param.name2", "testParam2");
            props.setProperty("http.param.value2", "testValue2");
            props.setProperty("http.timeout", "5000");
            
            File resourcesDir = new File("src/test/resources");
            if (!resourcesDir.exists()) {
                resourcesDir.mkdirs();
            }
            
            try (OutputStream output = new FileOutputStream("src/test/resources/" + TEST_PROPERTIES_FILE)) {
                props.store(output, "Test HTTP Config");
            }
        } catch (Exception e) {
            System.err.println("Failed to create test properties file: " + e.getMessage());
        }
    }
}

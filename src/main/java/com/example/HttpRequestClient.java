package com.example;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.StringJoiner;

public class HttpRequestClient {
    private static final int DEFAULT_TIMEOUT = 5000; // 5 seconds
    
    /**
     * Send HTTP GET request with parameters from user input
     * @return Response body as string
     */
    public String sendGetRequestWithUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter URL (e.g., https://httpbin.org/get): ");
            String url = scanner.nextLine();
            
            System.out.println("Enter timeout in milliseconds (default 5000): ");
            String timeoutStr = scanner.nextLine();
            int timeout = timeoutStr.isEmpty() ? DEFAULT_TIMEOUT : Integer.parseInt(timeoutStr);
            
            Map<String, String> params = new HashMap<>();
            
            System.out.println("How many parameters do you want to add? ");
            int paramCount = Integer.parseInt(scanner.nextLine());
            
            for (int i = 0; i < paramCount; i++) {
                System.out.println("Enter parameter " + (i + 1) + " name: ");
                String name = scanner.nextLine();
                
                System.out.println("Enter parameter " + (i + 1) + " value: ");
                String value = scanner.nextLine();
                
                params.put(name, value);
            }
            
            return sendGetRequest(url, params, timeout);
        } catch (Exception e) {
            System.err.println("Error during HTTP request: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * Send HTTP GET request with parameters from properties file
     * @param propertiesFilePath Path to properties file
     * @return Response body as string
     */
    public String sendGetRequestWithPropertiesFile(String propertiesFilePath) {
        try {
            Properties properties = loadProperties(propertiesFilePath);
            
            String url = properties.getProperty("http.url");
            if (url == null || url.isEmpty()) {
                throw new IllegalArgumentException("http.url not found in properties file");
            }
            
            int timeout = DEFAULT_TIMEOUT;
            String timeoutStr = properties.getProperty("http.timeout");
            if (timeoutStr != null && !timeoutStr.isEmpty()) {
                timeout = Integer.parseInt(timeoutStr);
            }
            
            Map<String, String> params = new HashMap<>();
            int paramIndex = 1;
            
            while (true) {
                String paramName = properties.getProperty("http.param.name" + paramIndex);
                String paramValue = properties.getProperty("http.param.value" + paramIndex);
                
                if (paramName == null || paramValue == null) {
                    break;
                }
                
                params.put(paramName, paramValue);
                paramIndex++;
            }
            
            return sendGetRequest(url, params, timeout);
        } catch (Exception e) {
            System.err.println("Error during HTTP request: " + e.getMessage());
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
    /**
     * Send HTTP GET request with specified URL, parameters and timeout
     * @param url Base URL
     * @param params Query parameters
     * @param timeout Connection timeout in milliseconds
     * @return Response body as string
     */
    private String sendGetRequest(String url, Map<String, String> params, int timeout) 
            throws Exception {
        // Build parameters with URL encoding
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + 
                   URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String encodedParams = sj.toString();
        String fullUrl = url + (encodedParams.isEmpty() ? "" : "?" + encodedParams);
        
        System.out.println("Executing request to: " + fullUrl);
        System.out.println("Parameters: " + encodedParams);
        
        // Execute the GET request
        HttpResult httpResponse = doGet(fullUrl);
        
        // Log details as per requirements
        System.out.println("\n--- HTTP Request Details ---");
        System.out.println("URL: " + fullUrl);
        System.out.println("Parameters: " + encodedParams);
        
        System.out.println("\n--- HTTP Response Details ---");
        System.out.println("Status: " + httpResponse.getStatusCode());
        System.out.println("Body: " + httpResponse.getBody());
        
        System.out.println("\n--- HTTP Headers ---");
        StringBuilder responseHeader = new StringBuilder();
        for (Header h : httpResponse.getHeaders()) {
            System.out.println(h.getName() + ": " + h.getValue());
            responseHeader.append(h.getName()).append(":").append(h.getValue()).append(";");
        }
        
        // Log complete response in the specified format
        System.out.println("\n--- Complete Response Log ---");
        String transId = "sample-trans-id"; // This would typically come from input
        String messageId = "sample-message-id"; // This would typically come from input
        
        System.out.println("QueueListeners::umobileHttpRequest::trans_id::[" + transId + "]"
            + "::messageid::[" + messageId + "]" 
            + "[HttpRequest::" + fullUrl + "][Body:" + httpResponse.getBody().trim() + "]"
            + "::[HttpResponse::" + httpResponse.getStatusCode() + "]"
            + "::[HttpRequestHeader::" + responseHeader + "]"
            + "::[HttpResponseBody::" + httpResponse.getUrlEncodedFormEntity().trim() + "]");
        
        return httpResponse.getBody();
    }
    
    /**
     * Load properties from file
     * @param propertiesFilePath Path to properties file
     * @return Properties object
     */
    private Properties loadProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = HttpRequestClient.class.getClassLoader().getResourceAsStream(propertiesFilePath)) {
            if (input == null) {
                // Try as absolute path if not found in resources
                try (FileInputStream fileInput = new FileInputStream(propertiesFilePath)) {
                    properties.load(fileInput);
                }
            } else {
                properties.load(input);
            }
        }
        return properties;
    }
    
    /**
     * Execute a HTTP GET request 
     * @param url The URL to send the request to
     * @return The HTTP result containing status code, response body and headers
     * @throws Exception If any error occurs during the request
     */
    public HttpResult doGet(String url) throws Exception {
        // Declare HTTP GET request
        HttpGet httpGet = new HttpGet(url);
        
        // Load configuration information
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_TIMEOUT)
                .setConnectionRequestTimeout(DEFAULT_TIMEOUT)
                .setSocketTimeout(DEFAULT_TIMEOUT)
                .build();
        
        httpGet.setConfig(config);

        // Add standard headers
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String strDate = formatter.format(date);
        
        httpGet.addHeader("Date", strDate);
        httpGet.setHeader("Host", httpGet.getURI().getHost());
        httpGet.setHeader("Connection", "keep-alive");

        // Execute request using a closeable HTTP client
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // Create and return HttpResult
            return new HttpResult(
                response.getStatusLine().getStatusCode(),
                EntityUtils.toString(response.getEntity(), "UTF-8"),
                response.getAllHeaders(),
                url
            );
        }
    }
}

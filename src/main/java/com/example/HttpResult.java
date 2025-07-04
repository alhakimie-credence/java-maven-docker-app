package com.example;

import org.apache.http.Header;

/**
 * Class to encapsulate HTTP response data
 */
public class HttpResult {
    private int statusCode;
    private String body;
    private Header[] headers;
    private String url;
    
    public HttpResult(int statusCode, String body, Header[] headers, String url) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
        this.url = url;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getBody() {
        return body;
    }
    
    public Header[] getHeaders() {
        return headers;
    }
    
    public String getUrl() {
        return url;
    }
    
    /**
     * Get URL encoded form entity as a string
     * This is a convenience method for compatibility with sample code
     */
    public String getUrlEncodedFormEntity() {
        return body; // In a GET request, the body contains the response
    }
}

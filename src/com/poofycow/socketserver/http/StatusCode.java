package com.poofycow.socketserver.http;

/**
 * Status code for HTTP messages
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public enum StatusCode {
    OK(200, "OK\r\n"),
    BAD_REQUEST(400, "Bad Request\r\n"),
    NOT_FOUND(404, "Not Found\r\n"),
    TEAPOT(418, "I'm a teapot\r\n");
    
    private int code;
    private String statusText;
   
    
    private StatusCode(int code, String statusText) {
        this.code = code;
        this.statusText = statusText;
    }
    
    /**
     * The status code
     * @return The status code
     */
    public int getCode() {
        return this.code;
    }
    
    /**
     * The status text
     * @return The status text
     */
    public String getStatusText() {
        return this.statusText;
    }
    
    /**
     * Returns a parsed header
     * @return The parsed header
     */
    public String getHeader() {
        return "HTTP/1.1 " + this.code + " " + this.statusText;
    }
}

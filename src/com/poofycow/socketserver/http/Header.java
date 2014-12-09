package com.poofycow.socketserver.http;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * HTTP header
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public enum Header {
    BASIC("Server: T-Connect\r\nContent-Length:0"),
    HTML( "Server: T-Connect\r\nContent-Type: text/html; charset=UTF-8" ),
    HTML_GZIP( "Server: T-Connect\r\nContent-Type: text/html; charset=UTF-8\r\nContent-Encoding: gzip" ),
    JSON( "Server: T-Connect\r\nContent-Type: application/json; charset=UTF-8" ),
    JSON_GZIP( "Server: T-Connect\r\nContent-Type: application/json; charset=UTF-8\r\nContent-Encoding: gzip" );

    private String header;

    private Header( String header ) {
        this.header = header;
    }

    /**
     * Gets the HTTP header
     * @return THe HTTP header
     */
    public String getHeader() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss z", Locale.forLanguageTag( "nl" ));
        dateFormat.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
        
        return this.header + "\r\nDate:" + dateFormat.format( Calendar.getInstance().getTime() ) + "\r\n\r\n";
    }
    
    /**
     * Parses an HTTP header
     * @param header The header to parse
     * @return The parsed result
     */
    public static Header parse(String header) {
        String lHeader = header.toLowerCase();
        if(lHeader.contains( "content-type: application/json" )) {
            if(lHeader.contains( "content-encoding: gzip" )) {
                return Header.JSON_GZIP;
            } else {
                return Header.JSON;
            }
        } else if (lHeader.contains( "content-type: text/html" )){
            if(lHeader.contains( "content-encoding: gzip" )) {
                return Header.HTML_GZIP;
            } else {
                return Header.HTML;
            }
        } else {
            return Header.BASIC;
        }
    }
}

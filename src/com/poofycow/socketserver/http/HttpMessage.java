package com.poofycow.socketserver.http;

import com.poofycow.socketserver.SocketClient;
import com.poofycow.socketserver.SocketMessage;

/**
 * Generic HTTP message
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public abstract class HttpMessage extends SocketMessage {
    private Header header;
    private String requestHeader;

    /**
     * Constructor
     * @param client The client that sent the message
     * @param requestHeaders The headers of the message
     */
    public HttpMessage( SocketClient client, String[] requestHeaders ) {
        super( client );

        StringBuilder builder = new StringBuilder();
        for ( String s : requestHeaders ) {
            builder.append( s + "\r\n" );
        }

        this.requestHeader = builder.toString();
        this.header = Header.parse( requestHeader );
    }

    /**
     * Gets the request headers
     * @return The request headers
     */
    public String getRequestHeader() {
        return this.requestHeader;
    }

    /**
     * Gets the HTTP header
     * @return THe HTTP header
     */
    public Header getHeader() {
        return this.header;
    }
}

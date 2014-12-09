package com.poofycow.socketserver;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A basic socket message
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public abstract class SocketMessage {
    protected SocketClient client;

    /**
     * Constructor
     * @param client The client that sent the message
     */
    public SocketMessage( SocketClient client ) {
        this.client = client;
    }

    /**
     * Returns the client that sent the message
     * @return The client
     */
    public SocketClient getClient() {
        return this.client;
    }

    /**
     * Sends a response back to the client
     * @param data The data to send
     * @throws IOException Thrown when sending went wrong
     */
    public void sendResponse( String data ) throws IOException {
        OutputStream stream = client.getOutputStream();

        stream.write( data.getBytes() );

        stream.flush();
        stream.close();
    }
}

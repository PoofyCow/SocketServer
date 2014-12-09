package com.poofycow.socketserver.http;

import com.poofycow.socketserver.listeners.SocketConnectedListener;

/**
 * Listener on the HTTP server
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public abstract class HttpSocketListener extends SocketConnectedListener {
    /**
     * Called when a GET message was received
     * @param msg The message
     */
    public abstract void onGetMessage(GetMessage msg);
    
    /**
     * Called when a POST message was received
     * @param msg THe message
     */
    public abstract void onPostMessage(PostMessage msg);
}

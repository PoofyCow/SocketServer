package com.poofycow.socketserver.listeners;

import com.poofycow.socketserver.SocketClient;

/**
 * Listens to a socket server
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public abstract class SocketConnectedListener implements SocketListener {
    
    /**
     * Triggered when a client connects to the server
     * @param client The connected client
     */
    public abstract void onClientConnected(SocketClient client);
}

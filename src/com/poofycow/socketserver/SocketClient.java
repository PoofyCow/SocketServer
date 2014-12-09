package com.poofycow.socketserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import com.poofycow.utils.logger.LogLevel;
import com.poofycow.utils.logger.Logger;

/**
 * A client that connected to the socketserver
 * 
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public class SocketClient {
    private Socket socket;
    
    /**
     * Constructor
     * 
     * @param socket
     *            The connected socket
     */
    public SocketClient(Socket socket) {
        this.socket = socket;
    }
    
    /**
     * Gets the remote address of the connection
     * 
     * @return The remote address
     */
    public SocketAddress getAddress() {
        return this.socket.getRemoteSocketAddress();
    }
    
    /**
     * Gets the outputstream for this connection
     * 
     * @return The outputstream
     */
    public OutputStream getOutputStream() {
        try {
            return this.socket.getOutputStream();
        } catch (IOException e) {
            Logger.log(LogLevel.WARNING, "SocketCLient", "Could not get outpustream");
            
            return null;
        }
    }
}

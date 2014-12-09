package com.poofycow.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.poofycow.server.Server;
import com.poofycow.socketserver.listeners.SocketConnectedListener;
import com.poofycow.utils.logger.LogLevel;
import com.poofycow.utils.logger.Logger;

/**
 * The base SocketServer, for... sockets
 * 
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public abstract class SocketServer extends Thread implements Server {
    private final static int       BACKLOG = 500;
    private ServerSocket           socket;
    private boolean                running;
    protected List<SocketConnectedListener> listeners;
    
    /**
     * Constructor
     * 
     * @param port
     *            The port to bind to
     */
    protected SocketServer(int port) {
        Logger.log(LogLevel.INFO, "SocketServer", "Initializing Server");
        this.listeners = new ArrayList<SocketConnectedListener>();
        
        this.setName("SocketServer - " + port);
        
        try {
            Logger.log(LogLevel.INFO, "SocketServer", "Binding server on port " + port);
            
            socket = new ServerSocket(port, BACKLOG);
        } catch (IOException e) {
            Logger.log(LogLevel.FATAL, "SocketServer_init", "Error initializing socketserver", e);
        }
    }
    
    /**
     * Register a listener to the server
     * 
     * @param listener
     *            THe listener to add
     */
    public void registerListener(SocketConnectedListener listener) {
        this.listeners.add(listener);
    }
    
    /**
     * Unregisters a listener from the server
     * 
     * @param listener
     *            The listener to unregister
     */
    public void unregisterListener(SocketConnectedListener listener) {
        this.listeners.remove(listener);
    }
    
    @Override
    public void interrupt() {
        Logger.log(LogLevel.INFO, "SocketServer", "SocketServer is stopping");
        this.running = false;
        super.interrupt();
    }
    
    @Override
    public void run() {
        running = true;
        Logger.log(LogLevel.INFO, "SocketServer", "SocketServer is ready to accept incomming connections");
        
        while (running) {
            try {
                final Socket clientSocket = socket.accept();
                
                if (clientSocket != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            SocketClient client = new SocketClient(clientSocket);
                            
                            for (SocketConnectedListener listener : listeners) {
                                try {
                                    listener.onClientConnected(client);
                                } catch (Exception e) {
                                    
                                }
                            }
                            
                            handle(clientSocket);
                        }
                    }.start();
                }
                
            } catch (IOException e) {
                Logger.log(LogLevel.FATAL, "SocketServer_run", "Error processing incomming connection", e);
            }
        }
    }
    
    /**
     * Called when a channel was accepted and needs it's data handled
     * 
     * @param socket
     *            The socket that was accepted
     */
    public abstract void handle(Socket socket);
    
    public boolean isRunning() {
        return this.running;
    }
}

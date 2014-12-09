package com.poofycow.server;

/**
 * Generic server interface enforcing the basic server methods
 * @author Poofy Cow
 *
 */
public interface Server {
    public void start();
    public void kill();
    public boolean isRunning();
}

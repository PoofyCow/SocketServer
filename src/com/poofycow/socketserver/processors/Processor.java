package com.poofycow.socketserver.processors;

import java.nio.ByteBuffer;

/**
 * Generic processor
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public abstract class Processor {
    public Processor() {
        
    }
    
    /**
     * Processes input, bytebuffer to string
     * @param input The input
     * @return The processed result
     */
    public abstract String inputProcess(ByteBuffer input);
    
    /**
     * Processes output, string to bytebuffer
     * @param input The input
     * @return The processed result
     */
    public abstract ByteBuffer outputProcess(String input);
}

package com.poofycow.socketserver.encoders;


/**
 * Generic encoder
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public abstract class Encoder {    
    /**
     * Decodes data from string to the specified class
     * @param input The string to decode
     * @param clazz The class to decode to
     * @return The decoded data
     */
    public abstract <T> T decode(String input, Class<?> clazz);
    
    /**
     * Encodes an object to a string
     * @param input The object to encode
     * @return The encoded string
     */
    public abstract <T> String encode(T input);
    
}

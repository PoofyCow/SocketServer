package com.poofycow.socketserver.encoders;

/**
 * Types that can be encoded/decoded from/into
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public enum EncodeType {
    JSON(JSONEncoder.class);     
    
    private Class<? extends Encoder> encoder;
    
    private EncodeType(Class<? extends Encoder> encoder) {
        this.encoder = encoder;
    }
    
    public Class<?> getEncoderClass() {
        return this.encoder;
    }
}

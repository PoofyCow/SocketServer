package com.poofycow.socketserver.encoders;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.poofycow.utils.logger.LogLevel;
import com.poofycow.utils.logger.Logger;

/**
 * Encodes and decodes data into and from json
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public class JSONEncoder extends Encoder {
    Gson gson;

    /**
     * 
     * Constructor
     */
    public JSONEncoder() {
        
        
        gson = new GsonBuilder().setDateFormat( "EEE, dd MMM yyyy HH:mm:ss Z" ).create();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public < T > T decode( String input, Class<?> clazz ) {
        //Class< ? > clazz = ( (T) new Object() ).getClass();

        Constructor< ? > ctor;
        try {
            ctor = clazz.getConstructor();

            T obj = (T) ctor.newInstance();

            obj = (T) gson.fromJson( input, clazz );
            
            return obj;

        } catch ( NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
            Logger.log( LogLevel.WARNING, "JSONEncoder", "Error decoding json", e );

            return null;
        }
    }
    
    @SuppressWarnings( "unchecked" )
    public <T> T decode(JsonElement input, Class<?> clazz) {
        Constructor< ? > ctor;
        try {
            ctor = clazz.getConstructor();

            T obj = (T) ctor.newInstance();

            obj = (T) gson.fromJson( input, clazz );
            
            return obj;

        } catch ( NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) {
            Logger.log( LogLevel.WARNING, "JSONEncoder", "Error encoding json", e );

            return null;
        }
    }

    @Override
    public < T > String encode( T input ) {
    	if(input instanceof String)
    		return "{\"val\":\"" + (String) input + "\"}";
    	
        return gson.toJson( input );
    }

}

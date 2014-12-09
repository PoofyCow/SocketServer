package com.poofycow.socketserver.processors;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Processor for GZIP
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public class GZIPProcessor extends Processor {

    @Override
    public String inputProcess( ByteBuffer input ) {
        try {            
            GZIPInputStream gis = new GZIPInputStream( new ByteArrayInputStream( input.array() ) );
            BufferedReader bf = new BufferedReader( new InputStreamReader( gis, "UTF-8" ) );
            String outStr = "";
            String line;
            while ( ( line = bf.readLine() ) != null ) {
                outStr += line;
            }
            System.out.println( "Output String lenght : " + outStr.length() );
            return outStr;
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ByteBuffer outputProcess( String input ) {
        try {
            if ( input == null || input.length() == 0 ) {
                return null;
            }
            System.out.println( "String length : " + input.length() );
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream( out );
            gzip.write( input.getBytes() );
            gzip.close();
            return ByteBuffer.wrap( out.toByteArray() );
        } catch ( IOException e ) {
            e.printStackTrace();
            return null;
        }
    }

}

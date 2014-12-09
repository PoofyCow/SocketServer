package com.poofycow.socketserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import com.poofycow.socketserver.SocketClient;

/**
 * An HTTP get message
 * 
 * @author Poofy Cow
 * @version 2.0
 * @since 2.0
 * @date Nov 4, 2014
 */
public class GetMessage extends HttpMessage {
    private String                  requestUrl;
    private HashMap<String, String> params;
    
    /**
     * Constructor
     * 
     * @param client
     *            The client that sent the message
     * @param headers
     *            THe message headers
     */
    public GetMessage(SocketClient client, String[] headers) {
        super(client, headers);
        params = new HashMap<String, String>();
        
        if (headers.length >= 2) {
            String temp = headers[0].split(" ")[1];
            if (temp.contains("?")) {
                requestUrl = temp.substring(0, temp.indexOf("?"));
                
                String[] p = temp.substring(temp.indexOf("?")).split("&");
                
                for (String param : p) {
                    String[] sp = param.split("=");
                    this.params.put(sp[0], sp[1]);
                }
            } else {
                requestUrl = temp;
            }
        }
    }
    
    public Map<String, String> getParams() {
        return this.params;
    }
    
    /**
     * Gets the request url
     * 
     * @return The request url
     */
    public String getRequestUrl() {
        return this.requestUrl;
    }
    
    /**
     * Sends a response to the connected client
     * 
     * @param responseHeader
     *            The header of the response
     * @param code
     *            The code of the response
     * @param response
     *            The actual response data
     * @throws IOException
     *             Thrown when something went wrong in sending the response
     */
    public void sendResponse(Header responseHeader, StatusCode code, String response) throws IOException {
        OutputStream stream = client.getOutputStream();
        
        if (this.getHeader() == Header.HTML_GZIP || this.getHeader() == Header.JSON_GZIP) {
            if(responseHeader == Header.HTML)
                responseHeader = Header.HTML_GZIP;
            else if(responseHeader == Header.JSON)
                responseHeader = Header.JSON_GZIP;
            
            
            GZIPOutputStream gstream = new GZIPOutputStream(stream);
            stream.write((code.getHeader() + responseHeader.getHeader()).getBytes());
            gstream.write(response.getBytes());
            gstream.flush();
        } else {
            stream.write((code.getHeader() + responseHeader.getHeader()).getBytes());
            stream.write(response.getBytes());
        }
        
        stream.flush();
        stream.close();
    }
}

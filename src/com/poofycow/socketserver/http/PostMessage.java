package com.poofycow.socketserver.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.poofycow.socketserver.SocketClient;

/**
 * A post message
 * 
 * @author Poofy Cow
 * @version 1.0
 * @since 1.0
 * @date Nov 4, 2014
 */
public class PostMessage extends HttpMessage {
    private String                  requestUrl;
    private String[]                data;
    private HashMap<String, String> params;
    private boolean                 multiPart;
    private File                    file;
    
    /**
     * Constructor
     * 
     * @param client
     *            The client the message came from
     * @param headers
     *            The headers of the message
     * @param data
     *            The data contained in the message
     */
    public PostMessage(SocketClient client, String[] headers, String[] data) {
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
        this.data = data;
    }
    
    public PostMessage(SocketClient client, String[] headers, String[] data, File file) {
        this(client, headers, data);
        this.file = file;
        this.multiPart = true;
    }
    
    public Map<String, String> getParams() {
        return params;
    }
    
    /**
     * Gets the request url of the message
     * 
     * @return The request url
     */
    public String getRequestUrl() {
        return this.requestUrl;
    }
    
    public boolean isMultipart() {
        return this.multiPart;
    }
    
    public File getFile() {
        return this.file;
    }
    
    /**
     * Gets the data of the message
     * 
     * @return The data
     */
    public String[] getData() {
        return this.data;
    }
    
    /**
     * Sends a response back to the client
     * 
     * @param responseHeader
     *            The header of the response
     * @param code
     *            THe response code
     * @throws IOException
     *             Thrown when something went wrong in writing the message
     */
    public void sendResponse(Header responseHeader, StatusCode code) throws IOException {
        OutputStream stream = client.getOutputStream();
        
        stream.write((code.getHeader() + responseHeader.getHeader()).getBytes());
        
        stream.flush();
        stream.close();
    }
}

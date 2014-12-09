package com.poofycow.socketserver.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;

import com.poofycow.socketserver.SocketClient;
import com.poofycow.socketserver.SocketMessage;
import com.poofycow.socketserver.SocketServer;
import com.poofycow.socketserver.listeners.SocketConnectedListener;
import com.poofycow.utils.logger.LogLevel;
import com.poofycow.utils.logger.Logger;

/**
 * Server for the HTTP protocol
 * 
 * @author Poofy Cow
 * @version 2.1
 * @since 2.0
 * @date Nov 4, 2014
 */
public class HttpSocketServer extends SocketServer {
    private SocketClient client;
    private boolean      running;
    
    /**
     * Constructor
     * 
     * @param port
     *            The port to bind to
     */
    public HttpSocketServer(int port) {
        super(port);
        
        this.setName("HttpSocketServer - " + port);
    }
    
    @Override
    public void handle(Socket socket) {
        running = true;
        
        Logger.log(LogLevel.INFO, "HttpSocketServer", "New http connection from: " + socket.getRemoteSocketAddress().toString());
        
        this.client = new SocketClient(socket);
        
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            
            try {
                while (running) {
                    int read;
                    while ((read = socket.getInputStream().read(buffer)) > 0) {
                        byteStream.write(buffer, 0, read);
                    }
                    
                    Logger.log(LogLevel.DEBUG, "HttpSocketServer", "Got pakket");
                    
                    if (read == -1) {
                        Logger.log(LogLevel.DEBUG, "HttpSocketServer", "Packet was empty");
                        break;
                    }
                    
                    parse(byteStream.toString(), socket);
                }
            } catch (ClosedChannelException e) {
                Logger.log(LogLevel.INFO, "HttpSocketServer", "Channel closed");
            }
            
        } catch (IOException e) {
            
        }
        
        Logger.log(LogLevel.INFO, "HttpSocketServer", "Channel closed");
    }
    
    private void parse(String packet, Socket socket) throws IOException {
        Logger.log(LogLevel.DEBUG, "HttpSocketServer", "Parsing");
        
        String[] splitData = packet.split("\r\n\r\n");
        
        String[] headers = splitData[0].split("\r\n");
        String[] data = null;
        if (splitData.length > 1) {
            byte[] bData = splitData[1].getBytes();
            byte[] cData = null;
            
            for (int i = 0; i < bData.length; i++) {
                if (bData[i] == 0) {
                    cData = new byte[i];
                    cData = Arrays.copyOfRange(bData, 0, i);
                    break;
                }
            }
            
            if (cData == null)
                data = new String(bData).split("&");
            else
                data = new String(cData).split("&");
        }
        
        String[] requestData = headers[0].split(" ");
        
        Logger.log(LogLevel.DEBUG, "HttpSocketServer", "Parsing http headers");
        switch (requestData[0]) {
            case "GET":
                Logger.log(LogLevel.INFO, "HttpSocketServer", "GET request");
                GetMessage getMsg = new GetMessage(client, headers);
                
                Logger.log(LogLevel.DEBUG, "HttpSocketServer", "dispatching");
                dispatch(getMsg);
                break;
            case "POST":
                Logger.log(LogLevel.INFO, "HttpSocketServer", "POST request");
                
                PostMessage postMsg = new PostMessage(client, headers, data);
                
                Logger.log(LogLevel.DEBUG, "HttpSocketServer", "dispatching");
                dispatch(postMsg);
                break;
            default:
                Logger.log(LogLevel.WARNING, "HttpSocketServer", "invalid request: " + requestData[0] + ((requestData.length > 1) ? "\n" + requestData[1] : ""));
                new GetMessage(client, headers).sendResponse(Header.HTML, StatusCode.BAD_REQUEST, "Bad Request");
                break;
        }
    }
    
    private void dispatch(SocketMessage msg) {
        if (msg instanceof HttpMessage) {
            for (SocketConnectedListener listener : super.listeners) {
                if (listener instanceof HttpSocketListener) {
                    if (msg instanceof GetMessage) {
                        ((HttpSocketListener) listener).onGetMessage((GetMessage) msg);
                    } else if (msg instanceof PostMessage) {
                        ((HttpSocketListener) listener).onPostMessage((PostMessage) msg);
                    }
                }
            }
        }
    }
    
    @Override
    public boolean isRunning() {
        return running;
    }
    
    @Override
    public void kill() {
        this.running = false;
        super.interrupt();
    }
}

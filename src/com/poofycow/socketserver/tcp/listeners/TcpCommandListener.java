package com.poofycow.socketserver.tcp.listeners;

import java.io.InputStream;
import java.io.OutputStream;

import com.poofycow.socketserver.listeners.SocketListener;

public interface TcpCommandListener extends SocketListener {
	public abstract void onCommand(InputStream in, OutputStream out);
}

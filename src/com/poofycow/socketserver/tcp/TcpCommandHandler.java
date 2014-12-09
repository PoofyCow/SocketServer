package com.poofycow.socketserver.tcp;

import java.io.InputStream;

public abstract class TcpCommandHandler<T> {
	protected TcpCommandHandler() {
		
	}
	
	public abstract T handleCommand(InputStream inStream);
}

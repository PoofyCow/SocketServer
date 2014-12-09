package com.poofycow.socketserver.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;

import com.poofycow.socketserver.SocketServer;
import com.poofycow.socketserver.tcp.listeners.TcpCommandListener;
import com.poofycow.utils.logger.LogLevel;
import com.poofycow.utils.logger.Logger;

public class TcpSocketServer<T> extends SocketServer {
	private TcpCommandHandler<T> commandHandler;
	private HashMap<T, LinkedList<TcpCommandListener>> listeners;

	public TcpSocketServer(int port, TcpCommandHandler<T> commandHandler) {
		super(port);
		this.commandHandler = commandHandler;
	}

	@Override
	public void kill() {
		super.interrupt();
	}

	@Override
	public void handle(Socket socket) {
		try {
			T command = commandHandler.handleCommand(socket.getInputStream());

			if (listeners.containsKey(command)) {
				for (TcpCommandListener listener : listeners.get(command)) {
					new Thread() {
						@Override
						public void run() {
							this.setName("CommandListenerThread");
							try {
								synchronized (socket) { //In case if ****tards
									listener.onCommand(socket.getInputStream(),
											socket.getOutputStream());
								}
							} catch (IOException e) {
								Logger.log(
										LogLevel.SEVERE,
										"TcpSocketServer",
										"Error in command handler, could be socket related. Could also be the developer messed up.",
										e); // AKA possible ****tards
							} catch (Exception e) {
								Logger.log(
										LogLevel.FATAL,
										"TcpSocketServer",
										"Error in command listener, error should not be thrown this far up.",
										e); // AKA ****tards
							}
						}
					}.start();
				}
			}
		} catch (Exception e) {
			Logger.log(
					LogLevel.FATAL,
					"TcpSocketServer",
					"Error in command handler! This should never happen, pleas check your handler",
					e);
		}
	}

	public void registerCommandListener(T command, TcpCommandListener listener) {
		synchronized (listeners) {
			if (!listeners.containsKey(command))
				this.listeners.put(command,
						new LinkedList<TcpCommandListener>());

			this.listeners.get(command).add(listener);
		}
	}

	public void unregisterCommandListener(T command, TcpCommandListener listener) {
		synchronized (listeners) {
			if (listeners.containsKey(command)) {
				listeners.get(command).remove(listener);
			}
		}
	}
}

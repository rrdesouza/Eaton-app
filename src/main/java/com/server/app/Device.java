package com.server.app;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class Device implements Runnable {
	
	private int nMessage = 0;
	private Socket socket;
	private PrintStream message;

	@Override
	public void run() {
		try {
				socket = new Socket("localhost",20000);
				message = new PrintStream(socket.getOutputStream());

				while(!Thread.currentThread().isInterrupted()){
					//thread sleep random time and send a message
					Thread.sleep(ThreadLocalRandom.current().nextInt(1000,10000));
					nMessage = getnMessage() + 1;
					//true if device still running
					message.println("true");
				}
			} catch (IOException | InterruptedException e) {
				//System.out.println(Thread.currentThread().getName());
				if(message != null)
					message.println("false");
				//e.printStackTrace();
			}
		

	}

	public int getnMessage() {
		return nMessage;
	}

}

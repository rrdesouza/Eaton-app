package com.server.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
	private ArrayList<Thread> monitors = new ArrayList<Thread>();
	private ServerSocket server;
	private volatile int t = 0;
	private volatile boolean transfer = false;
	
	public Server(ServerSocket s) {
		this.server = s;
	}
	

	@Override
	public void run() {
		try {
			while(!Thread.currentThread().isInterrupted()) {
				Socket socket = server.accept();
				Monitor monitor = new Monitor(socket,this);
				Thread tm = new Thread (monitor);
				tm.start();
				monitors.add(tm); 
			}
		} catch (IOException e) {
			//Ignoring
			//e.printStackTrace();

		}

	}
	
	public synchronized void increase() throws InterruptedException {
		while(transfer) {
			wait();
		}
		this.transfer = true;
		this.t++;
		this.transfer = false;
		notifyAll();
	}
	
	public int getTotal() {
		return this.t;
	}
	
}

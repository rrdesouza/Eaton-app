package com.server.app;

import java.net.Socket;
import java.util.Scanner;

public class Monitor implements Runnable {

	private Socket socket;
	private Server server;
	private String m = "true";

    public Monitor(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
    	while(m.equals("true")) {
	    	Scanner message;
			try {
				message = new Scanner (socket.getInputStream());
				m = message.nextLine();
				server.increase();
			} 
	        catch (Exception e) {
	        	System.out.println("Monitor Erro");

			}
    	}
    }


}

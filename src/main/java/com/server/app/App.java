package com.server.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
	
	private static int i = 1;

    public static void main( String[] args ) throws IOException{
    	
    	Map<String, Thread> devices = new HashMap<String,Thread>();
    	ServerSocket server = new ServerSocket(20000);
    	Server s = new Server(server);
    	Thread ts = new Thread(s);
    	ts.start();
    	
    	//Print valid commands
    	getInstructions();

		System.out.println("\nWaiting a comand...");
    	Scanner input = new Scanner(System.in);
    	String[] command = input.nextLine().split(" ");
    	
    	
    	
    	while(!command[0].equals("exit")) {
    		//run the command
    		exeCommand(command, devices, s);
    		System.out.println("\nWaiting a comand...");
    		command = input.nextLine().split(" ");
    	}
    	input.close();
    	devices.forEach((k,v) -> v.interrupt());
    	devices.clear();
    	//ttotal number of read messages
    	System.out.println("Total Messages Read = " + s.getTotal());
    	server.close();
    }
    
    private static void exeCommand(String[] command, Map<String,Thread> devices, Server s) {
    	switch (command[0].toLowerCase()) {
    	case "c":{
			if(command.length>1) {
				int n;
				try {
					n = Integer.parseInt(command[1])>0 ? Integer.parseInt(command[1]) : 1;
				} catch (NumberFormatException e) {
    				System.out.println("Invalid");
    				break;

				}
				creatDevice(n ,devices, i);
				i = i+n;
			}
			else {
				creatDevice(1 ,devices, i);
				i++;
			}
    		
    		break;
		}
		case "d":{
			if(command.length <=1) {
				System.out.println("Invalid");
				break;
			}
			if(command[1].equals("all")) {
		    	devices.forEach((k,v) -> v.interrupt());
		    	devices.clear();
		    	break;
			}
			if(devices.containsKey(command[1])) {
				devices.get(command[1]).interrupt();
				devices.remove(command[1]);
				System.out.println("Device " + command[1] + " was interrupt" );

			}
			else {
				System.out.println("device not exist");
			}
			break;
		}
		case "t":
			System.out.println("Total Messages = " + s.getTotal());
			break;
		case "ls":{
			devices.forEach((k,v)-> System.out.print(k+" "));
			System.out.println();
			break;
		}
		default:
			System.out.println("Invalid");
		}
    }

	private static void creatDevice(int n, Map <String,Thread> devices, int i) {
		for(int m = 0; m<n; m++) {
			Device d = new Device();
    		Thread td = new Thread(d);
    		td.setName("d"+i);
    		devices.put("d"+i, td);
    		td.start();
    		i++;
    		System.out.println("Device " + td.getName() + " is ready");
		}
		
	}

	private static void getInstructions() {
		System.out.println("Instructions!\n"
				+ "commands		Function\n"
				+ "c		Create a new device\n"
				+ "c n		Create n devices\n"
				+ "ls		Show all devices\n"
				+ "t		Show total messages\n"
				+ "d 'name'	Delete device 'name'\n"
				+ "d all		Delete all devices\n"
				+ "exit		Close the execution");
	}
}

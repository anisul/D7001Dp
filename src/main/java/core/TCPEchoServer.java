package core;

//Requires a single command line arg - the port number
import java.net.*;	// need this for InetAddress, Socket, ServerSocket 

import java.io.*;	// need this for I/O stuff


public class TCPEchoServer {
	// main starts things rolling
	static public void main(String args[]) { 
		
		if (args.length != 2){
			throw new IllegalArgumentException("Must specify a port and client name!");
		}
		
		int port = Integer.parseInt(args[0]);
		String clientName = args[1];
		try { 
			// Create Server Socket (passive socket) 
			ServerSocket ss = new ServerSocket(port);
			
			while (true) { 
				Socket s = ss.accept();
				handleClient(s, clientName);
			}
			
		} catch (IOException e) {
			System.out.println("Fatal I/O Error !"); 
			System.exit(0);
			
		}
		
	}
	
	//this method handles one client
	// declared as throwing IOException - this means it throws 
	// up to the calling method (who must handle it!)
	//try taking out the "throws IOException" and compiling, 
	// the compiler will tell us we need to deal with this!
	
	static void handleClient(Socket s,String clientName) throws IOException
	{ 
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);

		printWriter.println("Welcome to EchoServer. Type 'adjo' to close.");
		String line;

		do {
			line = bufferedReader.readLine();
			if (line != null) {
				printWriter.println(clientName + " " + line);
			}
		} while (!line.trim().equals("adjo"));

		System.out.println("Client has left\n"); 
		s.close();
	}
	
}
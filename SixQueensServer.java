/*
 * Name: Cameron Hudson
 * File: SizQueensServer.java
 * 
 * Starts up a server for the six queens game and handles
 * routing connections between players.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SixQueensServer{
	
///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * Main Program
	 */
	public static void main(String[] args){
		// Parse command line arguments.
		if (args.length != 2) argNumError();
		String host = args[0];
		int port = 0;
		try{
			port = Integer.parseInt(args[1]);
			if(port < 0 || port > 65535)
				throw new Exception("Psych!  That's the wrong port!");
		} catch(Exception e){
			invalidPort(args[1]);
		}

		try{
			// Listen for connections from clients.
			ServerSocket serversocket = new ServerSocket();
			serversocket.bind (new InetSocketAddress (host, port));
			System.out.println("Started server on host " + host + " on port " + port);

			// Session management logic
			SixQueensModel model = null;
			for (;;){
				Socket socket = serversocket.accept();
				ViewProxy proxy = new ViewProxy (socket);
				if (model == null || model.isFinished()){
					model = new SixQueensModel();
					proxy.setViewListener(model);
				} else {
					proxy.setViewListener(model);
					model = null;
				}
			}
		} catch (IOException exc) {
			ioError();
		} catch (SecurityException exc) {
			securityError();
		}
	}

///////////////////////////////////////////////////////////////////////////////
//                             Private Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * argNumError
	 *
	 * Prints an error message for number of arguments
	 * 
	 * @param IOException exc
	 * @return None
	 */
	private static void argNumError(){
		System.err.println("Error: incorrect number of arguments.");
		serverUsage();
	}

	/**
	 * invalidPort
	 *
	 * Prints invalid port connection message
	 *
	 * @param String s
	 * @return None
	 */
	private static void invalidPort(String s){
		System.err.println("Error: " + s + " is not a valid port.");
		serverUsage();
	}


	/**
	 * ioError
	 *
	 * Prints an error message for IO
	 * 
	 * @param IOException exc
	 * @return None
	 */
	private static void ioError(){
		System.err.println ("Error: Cannot assign requested address (Bind failed)");
		serverUsage();
	}

	/**
	 * securityError
	 *
	 * Prints securityError message
	 *
	 * @param  SecurityException s
	 * @return None
	 */
	private static void securityError(){
		System.err.println("Error: Permission denied (Bind failed)");
		serverUsage();
	}

	/**
	 * serverUsage
	 *
	 * prints a uage message for the server.
	 *
	 * @param None
	 * @return None
	 */
	private static void serverUsage(){
		System.err.println("Usage: java SixQueensServer <host> <port>");
		System.exit(1);
	}
}


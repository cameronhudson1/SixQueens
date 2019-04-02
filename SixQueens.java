/*
 * Name: Cameron Hudson
 * File: SixQueens.java
 * 
 * Starts up a client for the six queens game and handles the game for
 * that player
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SixQueens{

///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * Main Program
	 *
	 * @param None
	 * @return None
	 */
	public static void main(String[] args){
		// Parse command line arguments.
		if (args.length != 3) argNumError();

		String host = args[0];
		int port = 0;
		try{
			port = Integer.parseInt(args[1]);
		} catch(Exception e){
			invalidPort(args[1]);
		}
		String name = args[2];

		try{
			// Open socket connection to server.
			Socket socket = new Socket();
			socket.connect (new InetSocketAddress (host, port));

			// Set up view and model proxy.
			SixQueensView view = SixQueensView.create(name);
			ModelProxy proxy = new ModelProxy(socket);
			view.setViewListener (proxy);
			proxy.setModelListener (view);

			// Inform server that a player has joined.
			proxy.join (view, name);
		} catch (Exception exc) {
			ioError(exc);
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
		clientUsage();
	}

	/**
	 * invalidPort
	 *
	 * Prints invalid port connection message
	 *
	 * @param None
	 * @return None
	 */
	private static void invalidPort(String s){
		System.err.println("Error: " + s + " is not a valid port.");
		clientUsage();
	}

	/**
	 * error
	 *
	 * prints an IO error message for the client.  Handles IO Exception.
	 *
	 * @param IOExeption exc
	 * @return None
	 */
	private static void ioError(Exception exc){
		System.err.println ("Error: Connection refused (Connection refused)");
		clientUsage();
	}

	/**
	 * clientUsage
	 *
	 * Prints usage message for client 
	 *
	 * @param None
	 * @return None
	 */
	private static void clientUsage(){
		System.err.println(
			"Usage: java SixQueensServer <host> <port> <playername>");
		System.exit(1);
	}
}
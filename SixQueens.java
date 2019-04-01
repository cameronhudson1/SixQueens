/*
====================================================================
Name: Cameron Hudson
File: SixQueens.java

Starts up a client for the six queens game and handles the game for
that player
=====================================================================
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
	 */
	public static void main(String[] args){
		// Parse command line arguments.
		if (args.length != 3) clientUsage();
		String host = args[0];
		int port = Integer.parseInt(args[1]);
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
		} catch (IOException exc) {
			error (exc);
		}
	}


///////////////////////////////////////////////////////////////////////////////
//                             Private Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * error
	 *
	 * prints an IO error message for the client.  Handles IO Exception.
	 * @param IOExeption
	 * @return None
	 */
	private static void error(IOException exc){
		System.err.println ("TicTacToe: I/O error");
		exc.printStackTrace (System.err);
		System.exit (1);
	}

	/**
	 * clientUsage
	 *
	 * 
	 * @param None
	 * @return None
	 */
	public static void clientUsage(){
		System.err.println("Usage: java SixQueensServer <host> <port> <playername>");
		System.exit(1);
	}
}
/*
====================================================================
Name: Cameron Hudson
File: SizQueensServer.java

Starts up a server for the six queens game and handles
routing connections between players.
=====================================================================
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
		if (args.length != 2) serverUsage();
		String host = args[0];
		int port = Integer.parseInt(args[1]);

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
		}
		catch (IOException exc){
			error (exc);
		}
	}

///////////////////////////////////////////////////////////////////////////////
//                             Private Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * serverUsage
	 *
	 * prints a uage message for the server.
	 *
	 * @param None
	 * @return None
	 */
	public static void serverUsage(){
		System.err.println("Usage: java SixQueensServer <host> <port>");
		System.exit(1);
	}


	/**
	 * clientUsage
	 *
	 * Prints an error message for the client
	 * 
	 * @param None
	 * @return None
	 */
	private static void error (IOException exc){
		System.err.println ("GoServer: I/O error");
		exc.printStackTrace (System.err);
		System.exit(1);
	}

}


/*
 * Name: Cameron Hudson
 * File: ViewProxy
 * 
 * Class ViewProxy provides a network proxy in the server for acessing
 * or communication with the client.
 * 
 * This program resides in the model and gives access to the view 
 */

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ViewProxy implements ModelListener{

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private ViewListener listener;

	/**
	 * ViewProxy
	 *
	 * Constructs a ViewProxy object for the SixQueensModel
	 *
	 * @param Socket socket
	 * @return None
	 */
	public ViewProxy(Socket socket) throws IOException{
		try{
			this.socket = socket;
			socket.setTcpNoDelay (true);
			out = new DataOutputStream (socket.getOutputStream());
			in = new DataInputStream (socket.getInputStream());
		} catch (IOException exc) {
			error (exc);
		}
	}

///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * setViewlistener
	 *
	 * sets the listener field to viewListener and starts a readerThread.
	 *
	 * @param ViewListener viewListener
	 * @return None
	 */
	public synchronized void setViewListener(ViewListener viewListener){
		this.listener = viewListener;
		new ReaderThread().start();
	}

	/**
	 * getViewlistener
	 *
	 * gets the listener ViewListener field.
	 *
	 * @param None
	 * @return ViewListener
	 */
	public ViewListener getViewListener(){
		return this.listener;
	}

	/**
	 * newGame
	 *
	 * Writes the appropriate encoding for a New Game
	 *
	 * 	Delimiter: N
	 *  Following Arguments: None
	 *
	 * @param None
	 * @return None
	 */
	public void newGame(){
		try{
			out.writeByte ('N');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

    /**
	 * setQueen
	 *
	 * Writes the appropriate encoding for setting a queen
	 *
	 * 	Delimiter: Q
	 *  Following Arguments: Byte r, Byte c
	 *
	 * @param int r
	 * @param int c
	 * @return None
	 */
    public void setQueen(int r, int c){
    	try{
			out.writeByte ('Q');
			out.writeByte (r);
			out.writeByte (c);
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

    /**
	 * setVisible
	 *
	 * Writes the appropriate encoding for setting a visible piece
	 *
	 * 	Delimiter: V
	 *  Following Arguments: Byte r, Byte c
	 *
	 * @param int r
	 * @param int c
	 * @return None
	 */
    public void setVisible(int r, int c){
    	try{
			out.writeByte ('V');
			out.writeByte (r);
			out.writeByte (c);
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

    /**
	 * waitingForPlayer
	 *
	 * Writes the appropriate encoding for waiting for a player message
	 *
	 * 	Delimiter: P
	 *  Following Arguments: None
	 *
	 * @param None
	 * @return None
	 */
    public void waitingForPartner(){
    	try{
			out.writeByte ('P');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

    /**
	 * yourTurn
	 *
	 * Writes the appropriate encoding for signaling this view's turn
	 *
	 * 	Delimiter: T
	 *  Following Arguments: None
	 *
	 * @param None
	 * @return None
	 */
    public void yourTurn(){
    	try{
			out.writeByte('T');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

    /**
	 * otherTurn
	 *
	 * Writes the appropriate encoding for signaling other view's turn
	 *
	 * 	Delimiter: Q
	 *  Following Arguments: UTF name
	 *
	 * @param String name
	 * @return None
	 */
    public void otherTurn(String name){
    	try{
			out.writeByte ('U');
			out.writeUTF (name);
			out.flush();
		} catch(IOException exc) {
			error (exc);
		}
    }

    /**
	 * youWin
	 *
	 * Writes the appropriate encoding for this view winning
	 *
	 * 	Delimiter: Y
	 *  Following Arguments: None
	 *
	 * @param None
	 * @return None
	 */
    public void youWin(){
    	try{
			out.writeByte ('Y');
			out.flush();
		} catch(IOException exc) {
			error (exc);
		}
    }

    /**
	 * otherWin
	 *
	 * Writes the appropriate encoding for other view winning
	 *
	 * 	Delimiter: X
	 *  Following Arguments: UTF name
	 *
	 * @param String name
	 * @return None
	 */
    public void otherWin(String name){
    	try{
			out.writeByte ('X');
			out.writeUTF (name);
			out.flush();
		} catch(IOException exc) {
			error (exc);
		}
    }

    /**
	 * quit
	 *
	 * Writes the appropriate encoding for a quit signal
	 *
	 * 	Delimiter: Z
	 *  Following Arguments: None
	 *
	 * @param None
	 * @return None
	 */
    public void quit(){
    	try{
			out.writeByte ('Z');
			out.flush();
		} catch(IOException exc) {
			error (exc);
		}
    }


///////////////////////////////////////////////////////////////////////////////
//                              Helper Class                                 //
///////////////////////////////////////////////////////////////////////////////

	private class ReaderThread extends Thread{
		/**
	 	* run
	 	*
	 	* Runs a thread for reading.  Instantited in a private class
	 	* which extends thread.  Reads the IOStreams from the sockets
	 	* and when messages are sent in, will decode them and call their 
	 	* corresponding methods
	 	*
	 	* 	ENCODING:
	 	*  	
	 	*
	 	* @param None
	 	* @return None
	 	*/
		public void run(){
			int op;
			int row, col;
			String name;
			try{
				for (;;){
					op = in.readByte();
					switch (op){
						case 'G':
							listener.newGame(ViewProxy.this);
							break;
						case 'J':
							name = in.readUTF();
							listener.join(ViewProxy.this, name);
							break;
						case 'S':
							row = in.readByte();
							col = in.readByte();
							listener.squareChosen(row, col, ViewProxy.this);
							break;
						case 'Z':
							listener.quit(ViewProxy.this);
							break;
						default:
							error ("Bad message");
							break;
					}
				}
			} catch (EOFException exc){
			
			} catch (IOException exc){
				error (exc);
			} finally {
				try{
					socket.close();
				} catch (IOException exc){
				
				}
			}
		}
	}

///////////////////////////////////////////////////////////////////////////////
//                             Private Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * error
	 *
	 * Prints an error message from a String
	 * 
	 * @param String msg
	 * @return None
	 */
	private static void error(String msg){
		System.err.printf ("ViewProxy: %s%n", msg);
		System.exit (1);
	}

	/**
	 * error
	 *
	 * Prints an error message from an IOException
	 * 
	 * @param IOException exc
	 * @return None
	 */
	private static void error(IOException exc){
		System.err.println ("ViewProxy: I/O error");
		exc.printStackTrace (System.err);
		System.exit (1);
	}
}
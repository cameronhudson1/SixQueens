/*
=====================================================================
Name: Cameron Hudson
File: ViewProxy

Class ViewProxy provides a network proxy in the server for acessing
or communication with the client.

This program resides in the model and gives access to the view
=====================================================================
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

	public synchronized void setViewListener(ViewListener viewListener){
		this.listener = viewListener;
		new ReaderThread().start();
	}

	public ViewListener getViewListener(){
		return this.listener;
	}

	public void newGame(){
		try{
			out.writeByte ('N');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

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

    public void setVisible(int r, int c){

    }

    public void waitingForPartner(){
    	try{
			out.writeByte ('P');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

    public void yourTurn(){
    	try{
			out.writeByte('T');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
    }

    public void otherTurn(String name){
    	try{
			out.writeByte ('U');
			out.writeUTF (name);
			out.flush();
		} catch(IOException exc) {
			error (exc);
		}
    }

    public void youWin(){

    }

    public void otherWin(String name){

    }

    public void draw(){

    }

    public void quit(){

    }


///////////////////////////////////////////////////////////////////////////////
//                              Helper Class                                 //
///////////////////////////////////////////////////////////////////////////////

	private class ReaderThread extends Thread{
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
							listener.join (ViewProxy.this, name);
							break;
						case 'S':
							row = in.readByte();
							col = in.readByte();
							listener.squareChosen(row, col, ViewProxy.this);
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

	private static void error(String msg){
		System.err.printf ("ViewProxy: %s%n", msg);
		System.exit (1);
	}

	/**
	 * Print an I/O error message and terminate the program.
	 */
	private static void error(IOException exc){
		System.err.println ("ViewProxy: I/O error");
		exc.printStackTrace (System.err);
		System.exit (1);
	}
}
/*
=====================================================================
Name: Cameron Hudson
File: ModelProxy

Class ModelProxy provides a network proxy in the client for accessing
or communicating with the server.

This program resides in the view and gives access to the model
=====================================================================
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ModelProxy implements ViewListener{

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private ModelListener listener;

	public ModelProxy(Socket socket) throws IOException{
		this.socket = socket;
		socket.setTcpNoDelay (true);
		out = new DataOutputStream (socket.getOutputStream());
		in = new DataInputStream (socket.getInputStream());
	}

///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////

	public void setModelListener(ModelListener modelListener){
		this.listener = modelListener;
		new ReaderThread().start();
	}

	public void newGame(ModelListener view){
		try{
			out.writeByte ('G');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
	}

	public void join(ModelListener view, String name){
		try{
			out.writeByte ('J');
			out.writeUTF (name);
			out.flush();
		}
		catch (IOException exc){
			error (exc);
		}
	}

	public void squareChosen(int row, int col, ModelListener view){
		try{
			out.writeByte('S');
			out.writeByte(row);
			out.writeByte(col);
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
	}

	public void quit(ModelListener view){
		try{
			out.writeByte('Z');
			out.flush();
		} catch (IOException exc) {
			error (exc);
		}
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
						case 'N':
							listener.newGame();
							break;
						case 'Q':
							row = in.readByte();
							col = in.readByte();
							listener.setQueen(row, col);
							break;
						case 'V':
							row = in.readByte();
							col = in.readByte();
							listener.setVisible(row, col);
							break;
						case 'P':
							listener.waitingForPartner();
							break;
						case 'T':
							listener.yourTurn();
							break;
						case 'U':
							name = in.readUTF();
							listener.otherTurn (name);
							break;
						case 'Y':
							listener.youWin();
							break;
						case 'X':
							name = in.readUTF();
							listener.otherWin(name);
							break;
						case 'Z':
							listener.quit();
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
		System.err.printf ("ModelProxy: %s%n", msg);
		System.exit (1);
	}

	/**
	 * Print an I/O error message and terminate the program.
	 */
	private static void error(IOException exc){
		System.err.println ("ModelProxy: I/O error");
		exc.printStackTrace (System.err);
		System.exit (1);
	}
}
import java.awt.Color;

/*
=====================================================================
Name: Cameron Hudson
File: Model Listener

Interface ModelListener specifies the interface for an object that is
triggered by events from the model object in the Six Queens game.

The view is a ModelListener, thus the functions of the view should
be implemented here.
=====================================================================
*/
public interface ModelListener{

	public void newGame();

	public void setQueen(int r, int c);

	public void setVisible(int r, int c);

	public void waitingForPartner();

	public void yourTurn();

	public void otherTurn(String name);

	public void youWin();

	public void otherWin(String name);

	public void draw();

	public void quit();
}
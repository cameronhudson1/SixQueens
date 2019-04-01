import java.awt.Color;

/*
=====================================================================
Name: Cameron Hudson
File: Model Listener

Interface ViewListener specifies the interface for an object that is
triggered by events from the view object in the Six Queens game.

The Model is a view listener, thus the functions provided by the
model should be implemented here
=====================================================================
*/
public interface ViewListener{
	public void newGame(ModelListener view);
	public void join(ModelListener view, String name);
	public void squareChosen(int row, int col, ModelListener view);
	public void quit(ModelListener view);
}
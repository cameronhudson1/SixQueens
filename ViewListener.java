import java.awt.Color;

/*
 * Name: Cameron Hudson
 * File: Model Listener
 * 
 * Interface ViewListener specifies the interface for an object that is
 * triggered by events from the view object in the Six Queens game.
 * 
 * The Model is a view listener, thus the functions provided by the
 * model should be implemented here
 *
 * All functions stubbed here are documented in the implementation class
 * as the implementation differs slightly in implementation.
 */
public interface ViewListener{

	/**
	 * newGame
	 *
	 * Starts a new game
	 *
	 * 	Delimiter: G
	 *  Following Arguments: None
	 *
	 * @param ModelListener view
	 * @return None
	 */
	public void newGame(ModelListener view);

	/**
	 * join
	 *
	 * joins a player to a new game and waits or starts game
	 *
	 * @param ModelListener view
	 * @param String name
	 * @return None
	 */
	public void join(ModelListener view, String name);

	/**
	 * squareChosen
	 *
	 * Invoked upon a square click being read by the
	 * ViewProxy.  Updates model and respinds
	 * accordingly
	 *
	 * @param int row
	 * @param int col
	 * @param ModelListener view
	 * @return None
	 */
	public void squareChosen(int row, int col, ModelListener view);

	/**
	 * quit
	 *
	 * When one client quits, this handles the cleanup
	 * process of the model and notifies the other view
	 * in the game
	 *
	 * @param ModelListener view
	 * @return None
	 */
	public void quit(ModelListener view);
}
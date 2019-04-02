import java.awt.Color;

/*
 * Name: Cameron Hudson
 * File: Model Listener
 * 
 * Interface ModelListener specifies the interface for an object that is
 * triggered by events from the model object in the Six Queens game.
 * 
 * The view is a ModelListener, thus the functions of the view should
 * be implemented here.
 *
 * All functions stubbed here are documented in the implementation class
 * as the implementation differs slightly in implementation.
 */
public interface ModelListener{

	/**
     * setQueen
     * 
     * Handles the swing thread generation for setting a queen
     *
     * @param int r
     * @param int c
     * @return None
     */
	public void newGame();

	/**
     * setQueen
     * 
     * Handles the swing thread generation for setting a queen
     *
     * @param int r
     * @param int c
     * @return None
     */
	public void setQueen(int r, int c);

    /**
     * setVisible
     * 
     * Handles the swing thread generation for setting a square to visible
     *
     * @param int r
     * @param int c
     * @return None
     */
	public void setVisible(int r, int c);

	/**
     * waitingForPartner
     * 
     * Handles the swing thread generation for setting message to
     * "Waiting for Partner"
     *
     * @param None
     * @return None
     */
	public void waitingForPartner();

	/**
     * yourTurn
     * 
     * Handles the swing thread generation for setting message to
     * "Your Turn"
     *
     * @param None
     * @return None
     */
	public void yourTurn();

	/**
     * otherTurn
     * 
     * Handles the swing thread generation for setting message to
     * "Other Turn"
     *
     * @param None
     * @return None
     */
	public void otherTurn(String name);

	/**
     * youWin
     * 
     * Handles the swing thread generation for setting message to
     * "You Win"
     *
     * @param None
     * @return None
     */
	public void youWin();

	/**
     * otherWin
     * 
     * Handles the swing thread generation for setting message to
     * "Other Win"
     *
     * @param None
     * @return None
     */
	public void otherWin(String name);

	/**
     * quit
     * 
     * Exits on quit of other view
     *
     * @param None
     * @return None
     */
	public void quit();
}
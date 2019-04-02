/*
* Name: Cameron Hudson
* File:SixQueensModel.java
* 
* Model the backend logic.  Handles instantiating a BoardState and
* listening to the ViewProxy for 
*/

import java.util.LinkedList;

public class SixQueensModel implements ViewListener{
	private BoardState board;
 	private String name1;
	private String name2;
	private ModelListener view1;
	private ModelListener view2;
	private ModelListener turn;
	private boolean isFinished;

	/**
	 * SixQueensModel
	 *
	 * Constructs a SixQueensModel object
	 *
	 * @param None
	 * @return None
	 */
	public SixQueensModel(){
		board = new BoardState();
	}

///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * newGame
	 *
	 * wrapper call to doNewGame().  Called by
	 * ViewProxy when it recievs a newGame request
	 *
	 * @param ModelListener view
	 * @return None
	 */
	public void newGame(ModelListener view){
		if(name2 != null){
			doNewGame();
		}
	}

	/**
	 * join
	 *
	 * joins a player to a new game and waits or starts game
	 *
	 * @param ModelListener view
	 * @param String name
	 * @return None
	 */
	public void join(ModelListener view, String name){
		if (name1 == null){
			name1 = name;
			view1 = view;
			view1.waitingForPartner();
		} else {
			name2 = name;
			view2 = view;
			doNewGame();
		}
	}

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
	public void squareChosen(int row, int col, ModelListener view){
		if (view != turn || board.isBlocked(row, col)){
			return;
		} else {
			setQueen(row, col, view);
		}
	}

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
	public void quit(ModelListener view){
		if (view1 != null)
			view1.quit();
		if (view2 != null)
			view2.quit();
		turn = null;
		isFinished = true;
	}

	/**
	 * isFinished
	 *
	 * Returns wether the game has been finished
	 *
	 * @param None
	 * @return Boolean
	 */
	public synchronized boolean isFinished(){
		return isFinished;
	}


///////////////////////////////////////////////////////////////////////////////
//                             Private Methods                               //
///////////////////////////////////////////////////////////////////////////////

	/**
	 * doNewGame
	 *
	 * Starts a new game of SixQueens
	 * @param None
	 * @return None
	 */
	private void doNewGame(){
		// Clear the board and inform the players.
		board.clear();
		view1.newGame();
		view2.newGame();

		// Player 1 gets the first turn.
		turn = view1;
		view1.yourTurn();
		view2.otherTurn (name1);
	}

	/**
	 * setQueen
	 *
	 * Sets a space in the board to a queen.  If a player
	 * hasn't won, it switched turns and notifies the view,
	 * otehrwise it responds with the appropriate winner
	 * and sets the game to finished.
	 *
	 * @param ModelListener view
	 * @return None
	 */
	private void setQueen(int row, int col, ModelListener view){
		board.setQueen(row, col);
		view1.setQueen(row, col);
		view2.setQueen(row, col);

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(board.isBlocked(i, j)){
					view1.setVisible(i, j);
					view2.setVisible(i, j);
				}
			}
		}

		if(board.checkWin()){
			// Current player wins.
			turn = null;
			if (view == view1){
				view1.youWin();
				view2.otherWin(name1);
			}
			else{
				view1.otherWin(name2);
				view2.youWin();
			}
		} else {
			if(turn == view1){
				// Currnently View1's turn
				turn = view2;
				view2.yourTurn();
				view1.otherTurn (name1);
			} else {
				// Currently View2's turn
				turn = view1;
				view1.yourTurn();
				view2.otherTurn (name1);
			}
		}

	}
}
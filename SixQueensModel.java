import java.util.LinkedList;

public class SixQueensModel implements ViewListener{
	private BoardState board;
 	private String name1;
	private String name2;
	private ModelListener view1;
	private ModelListener view2;
	private ModelListener turn;
	private boolean isFinished;

	public SixQueensModel(){
		board = new BoardState();
	}

///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////

	public void newGame(ModelListener view){
		if(name2 != null){
			doNewGame();
		}
	}

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

	public void squareChosen(int row, int col, ModelListener view){
		if (view != turn /* //TODO Or spot is not open */){
			return;
		} else {
			setQueen(row, col, view);
		}
	}

	public void quit(ModelListener view){
		if(view1 != null) view.quit();
		if(view2 != null) view.quit();
		turn = null;
		isFinished = true;
	}

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


	private void setQueen(int row, int col, ModelListener view){
		board.setQueen(row, col);
		view1.setQueen(row, col);
		view2.setQueen(row, col);

		if(board.checkWin()){
			//TODO implement win logic
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

public class BoardState{
	private boolean[][] queenBoard;
	private boolean[][] blockedBoard;

	public BoardState(){
		queenBoard = new boolean[6][6];
		blockedBoard = new boolean[6][6];
	}

	public void clear(){
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				queenBoard[i][j] = false;
				blockedBoard[i][j] = false;
			}
		}
	}

	public void setQueen(int r, int c){
		queenBoard[r][c] = true;
	}

	/**
	 * checkBoardWin
	 *
	 * Determines if a player has won the game and returns true if so.
	 * @param None
	 * @return None
	 */
	public boolean checkWin(){
		return false;
	}
}
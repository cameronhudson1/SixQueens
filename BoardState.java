/*
====================================================================
Name: Cameron Hudson
File: BoardState.java

Maintains the state of the baord in the model.
=====================================================================
*/
public class BoardState{
	private boolean[][] queenBoard;
	private boolean[][] blockedBoard;

	/**
	 * BoardState
	 *
	 * Constructs a BoardState object
	 *
	 * @param None
	 * @return None
	 */
	public BoardState(){
		// True means the space has a queen
		queenBoard = new boolean[6][6]; 

		// True means that the space is blocked	
		blockedBoard = new boolean[6][6];
	}


///////////////////////////////////////////////////////////////////////////////
//                              Public Methods                               //
///////////////////////////////////////////////////////////////////////////////


	/**
	 * isQueen
	 *
	 * Returns wether the board contains a queen at row and col
	 *
	 * @param int row
	 * @param int col
	 * @return Boolean
	 */
	public boolean isQueen(int row, int col){
		return this.queenBoard[row][col];
	}

	/**
	 * isBlocked
	 *
	 * Returns wether the board is blocked at row and col
	 *
	 * @param int row
	 * @param int col
	 * @return Boolean
	 */
	public boolean isBlocked(int row, int col){
		return this.blockedBoard[row][col];
	}

	/**
	 * clear
	 *
	 * Clears the board
	 *
	 * @param None
	 * @return None
	 */
	public void clear(){
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				queenBoard[i][j] = false;
				blockedBoard[i][j] = false;
			}
		}
	}

	/**
	 * setQueen
	 *
	 * Sets the board space at (row, col) to a queen and
	 * implicitly handles setting the blocked locations accordingly
	 *
	 * @param int r
	 * @return None
	 */
	public void setQueen(int row, int col){
		// Set Queen
		queenBoard[row][col] = true;

		// Handle the row and column blocking
		for(int i = 0; i < 6; i++){
			if(!isQueen(row, i)){
				blockedBoard[row][i] = true;
			}
			if(!isQueen(i, col)) {
				blockedBoard[i][col] = true;
			}
		}

		// Handle the top left diagonal blocking
		int tempRow = row;
		int tempCol = col;
		while(tempRow >= 0 && tempCol >= 0 ){
			if(!isQueen(tempRow, tempCol)){
				blockedBoard[tempRow][tempCol] = true;				
			}
			tempRow--;
			tempCol--;
		}

		// Handle the bottom left diagonal blocking
		tempRow = row;
		tempCol = col;
		while(tempRow >= 0 && tempCol < 6 ){
			if(!isQueen(tempRow, tempCol)){
				blockedBoard[tempRow][tempCol] = true;
			}
			tempRow--;
			tempCol++;
		}

		// Handle the top right diagonal blocking
		tempRow = row;
		tempCol = col;
		while(tempRow < 6 && tempCol >= 0 ){
			if(!isQueen(tempRow, tempCol)){
				blockedBoard[tempRow][tempCol] = true;
			}
			tempRow++;
			tempCol--;
		}

		// Handle the bottom left diagonal blocking
		tempRow = row;
		tempCol = col;
		while(tempRow < 6 && tempCol < 6 ){
			if(!isQueen(tempRow, tempCol)){
				blockedBoard[tempRow][tempCol] = true;
			}
			tempRow++;
			tempCol++;
		}
	}

	/**
	 * checkBoardWin
	 *
	 * Determines if a player has won the game and returns true if so.
	 * @param None
	 * @return Boolean
	 */
	public boolean checkWin(){
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(!this.isQueen(i, j) && !this.isBlocked(i, j)){
					return false;
				}
			}
		}
		return true;
	}
}



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
}
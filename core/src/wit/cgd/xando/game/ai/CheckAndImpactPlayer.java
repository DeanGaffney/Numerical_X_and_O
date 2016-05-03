package wit.cgd.xando.game.ai;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.Board.Symbol;

public class CheckAndImpactPlayer extends BasePlayer{

	public CheckAndImpactPlayer(Board board, Symbol symbol) {
		super(board, symbol);
		name = "CheckAndImpactPlayer";
	}

	@Override
	public int move() {
		//check your symbol for win
		for(int r = 0; r < 3;r++)
			for(int c = 0; c< 3; c++){
				//check your symbol for winning position.
				if(checkYourWin(r,c))return r*3+c;
			}

		//check enemy for win
		for(int r = 0; r < 3;r++)
			for(int c = 0; c< 3; c++){
				if(checkEnemyWin(r,c))return r*3+c;
			}
		
		return makeRandomMove();
			
	}

	//change this to check the remaining numbers that the player has left.
	private boolean checkYourWin(int row,int col){
		if(board.cells[row][col] != board.EMPTY)return false;
		board.cells[row][col] = board.currentPlayer.currentNumber;
		if(board.hasWon(board.currentPlayer.currentNumber, row, col)){
			board.cells[row][col] = board.EMPTY;
			return true;
		}
		board.cells[row][col] = board.EMPTY;
		return false;
	}

	//change this to check the remaining numbers that the player has left.
	private boolean checkEnemyWin(int row,int col){
		if(board.cells[row][col] != board.EMPTY)return false;
		board.cells[row][col] = opponentSymbol;
		if(board.hasWon(opponentSymbol, row, col)){
			board.cells[row][col] = board.EMPTY;
			return true;
		}
		board.cells[row][col] = board.EMPTY;
		return false;
	}

	//make a random move from the remaining numbers the player has left.
	private int makeRandomMove(){
		//check centre
		for(int move : new int[]{4})
			if(board.cells[move/3][move%3] == board.EMPTY)return move;

		//corners
		if(checkCorners(board)){
			int [] corners = new int [] {0,2,6,8};
			while(true){
				int move = corners[(int) (Math.random() * 3)];
				if(board.cells[move/3][move%3] == board.EMPTY)return move;
			}
		}

		//sides
		if(checkSides(board)){
			int sides[] = new int[]{1,3,5,7};
			while(true){
				int move = sides[(int) (Math.random() * 3)];
				if(board.cells[move/3][move%3] == board.EMPTY)return move;
			}
		}
		return -1;
	}

	private boolean checkCorners(Board board){
		//check corners
		for(int move : new int[]{0,2,6,8})
			if(board.cells[move/3][move%3] == board.EMPTY)return true;

		return false;
	}

	private boolean checkSides(Board board){
		for(int move :new int[]{1,3,5,7})
			if(board.cells[move/3][move%3] == board.EMPTY)return true;

		return false;
	}



}

package wit.cgd.xando.game.ai;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.Board.Symbol;

public class ImpactSpacePlayer extends BasePlayer {

	public ImpactSpacePlayer(Board board, Symbol symbol) {
		super(board, symbol);
		name = "ImpactSpacePlayer";
	}
	/*
	 * The **BASIC** impact player doesn't support the numerical tic tac toe very well,
	 * so I just made it check the centre and corners and play the first number of their list
	 * in the best position according the the rules of x and o.
	 * The other versions of the impact player deal with numerical x and o strategies.
	 */

	@Override
	public int move() {
		assert board.isDraw():"Shouldn't be here";
		//give player their number to play.
		currentNumber = numbers.get(0);
				
		//check centre
		for(int move : new int[]{4}){
			if(board.cells[move/3][move%3] == board.EMPTY)return move;
		}
		
		//corners
				for(int move : new int [] {0,2,6,8})
					if(board.cells[move/3][move%3] == board.EMPTY) return move;
		
		//sides
		for(int move : new int [] {1,3,5,7})
			if(board.cells[move/3][move%3] == board.EMPTY) return move;

		return -1;
	}

}

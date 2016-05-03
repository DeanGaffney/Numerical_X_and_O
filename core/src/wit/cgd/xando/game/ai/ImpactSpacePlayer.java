package wit.cgd.xando.game.ai;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;

public class ImpactSpacePlayer extends BasePlayer {

	public ImpactSpacePlayer(Board board, int symbol) {
		super(board, symbol);
		name = "ImpactSpacePlayer";
	}

	@Override
	public int move() {
		
		assert !board.isDraw():"Shouldn't be here";
		//check centre
		for(int move : new int[]{4})
			if(board.cells[move/3][move%3] == board.EMPTY)return move;
		
		//corners
				for(int move : new int [] {0,2,6,8})
					if(board.cells[move/3][move%3] == board.EMPTY) return move;
		
		//sides
		for(int move : new int [] {1,3,5,7})
			if(board.cells[move/3][move%3] == board.EMPTY) return move;

		return -1;
	}

}

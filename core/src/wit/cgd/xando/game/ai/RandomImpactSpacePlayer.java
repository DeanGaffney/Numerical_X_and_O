package wit.cgd.xando.game.ai;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;

public class RandomImpactSpacePlayer extends BasePlayer{

	public RandomImpactSpacePlayer(Board board, int symbol) {
		super(board, symbol);
		name = "RandomImpactSpacePlayer";
	}

	@Override
	public int move() {
		assert !board.isDraw():"Shouldn't be here";
		
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

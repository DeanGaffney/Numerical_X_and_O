package wit.cgd.xando.game.ai;

import java.util.Random;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;

public class RandomSpacePlayer extends BasePlayer{

	private static final String	TAG	= WorldRenderer.class.getName();
	Random rand;
	public RandomSpacePlayer(Board board, int symbol) {
		super(board, symbol);
		name = "RandomSpacePlayer";
	}

	@Override
	public int move() {
		assert board.isDraw() : "You should not be here!";
		while(true){
			int r = (int)(Math.random() * 3);
			int c = (int)(Math.random() * 3);
			if(board.cells[r][c] == board.EMPTY)return r*3+c;
		}
	}
}

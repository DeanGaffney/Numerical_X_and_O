package wit.cgd.xando.game.ai;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.WorldRenderer;

public class FirstSpacePlayer extends BasePlayer {

	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName(); 

	public FirstSpacePlayer(Board board, int symbol) {
		super(board, symbol);
		name = "FirstSpacePlayer";
	}

	@Override
	public int move() {
		for (int r=2; r>=0; --r)
			for (int c=0; c<3; ++c) 
				if (board.cells[r][c]==board.EMPTY) 
					return r*3+c;
		return -1;
		
	}

}

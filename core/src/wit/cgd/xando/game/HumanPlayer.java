/**
 * @file        HumanPlayer.java
 * @author      Dean Gaffney 20067423
 * @assignment  Creats a human player derived from BasePlayer.java
 * @brief       Controls all aspects of the human player for the game.
 *
 * @notes       Extends from BasePlayer
 * 				
 */
package wit.cgd.xando.game;

import wit.cgd.xando.game.Board.Symbol;

public class HumanPlayer extends BasePlayer {
	
	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName(); 
	
	public HumanPlayer(Board board, Symbol symbol) {
		super(board, symbol);
		human = true;
		name = "Human";
	}

	@Override
	public int move() {
		// human move handled in worldController
		return 0;
	}
}

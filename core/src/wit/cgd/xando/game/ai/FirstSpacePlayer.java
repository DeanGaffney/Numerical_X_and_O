/**
 * @file        FirstSpacePlayer.java
 * @author      Dean Gaffney 20067423
 * @assignment  Basic AI Strategy for playing the game.
 * @brief       Plays first space with first number.
 *
 * @notes       Basic strategy player finds the first free space and places,
	 			their first available number from their list of remaining numbers.
 * 				
 */
package wit.cgd.xando.game.ai;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.Board.Symbol;
import wit.cgd.xando.game.WorldRenderer;

public class FirstSpacePlayer extends BasePlayer {

	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName(); 

	public FirstSpacePlayer(Board board, Symbol symbol) {
		super(board, symbol);
		name = "FirstSpacePlayer";
	}

	
	@Override
	public int move() {
		for (int r=2; r>=0; --r)
			for (int c=0; c<3; ++c) 
				if (board.cells[r][c]==board.EMPTY) {
					if(!numbers.isEmpty())currentNumber = numbers.get(0);	//set the current number of the player here to the first number in the array.
					return r*3+c;
				}
		return -1;
	}
}

/**
 * @file        RandomSpacePlayer.java
 * @author      Dean Gaffney 20067423
 * @assignment  Basic AI Strategy for playing the game.
 * @brief       Plays random space with random number.
 *
 * @notes       Basic strategy player finds a random number and places it in a random space.
 * 				
 */
package wit.cgd.xando.game.ai;

import java.util.Random;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.Board.Symbol;
import wit.cgd.xando.game.WorldRenderer;

public class RandomSpacePlayer extends BasePlayer{

	private static final String	TAG	= WorldRenderer.class.getName();
	Random rand;
	public RandomSpacePlayer(Board board, Symbol symbol) {
		super(board, symbol);
		name = "RandomSpacePlayer";
	}
	
	/*
	 * Random space player generates a random free space and if its free takes that position.
	 * this player then also generates a random number from their list of numbers and plays that number.
	 */

	@Override
	public int move() {
		assert board.isDraw() : "You should not be here!";
		while(true){
			int r = (int)(Math.random() * 3);
			int c = (int)(Math.random() * 3);
			if(board.cells[r][c] == board.EMPTY){
				currentNumber = getRandomNumber();
				return r*3+c;
			}
		}
	}
	
	public int getRandomNumber(){
		assert numbers.isEmpty() : "Should be a draw";
		
		int randomNumber = 0;
		do{
			int index  = (int) (Math.random() * numbers.size());
			randomNumber = numbers.get(index);
		}while(!checkValidNumber(randomNumber));
		
		return randomNumber;
	}
}

package wit.cgd.xando.game;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wit.cgd.xando.game.util.AudioManager;
import wit.cgd.xando.game.util.Constants;

public class Board {

	@SuppressWarnings("unused")
	private static final String TAG = WorldRenderer.class.getName();

	public static enum GameState {
		PLAYING, DRAW, EVEN_WON, ODD_WON
	}

	public GameState gameState;

	public enum Symbol{EVEN,ODD}	//to divide players by even or odd 
	public Symbol playerSymbol;

	public final int EMPTY = 0;
	public int currentMove = 1;

	//used for board calculations and rendering textures.
	public final int one = 1;
	public final int two = 2;
	public final int three = 3;
	public final int four = 4;
	public final int five = 5;
	public final int six = 6;
	public final int seven = 7;
	public final int eight = 8;
	public final int nine = 9;

	public int[][] cells = new int[3][3];
	public ArrayList<Integer> usedNumbers = new ArrayList<Integer>();

	public BasePlayer firstPlayer, secondPlayer;
	public BasePlayer currentPlayer;
	public Stack<Integer> moves;		//used to undo moves

	public Board() {
		init();
	}

	public void init() {
		start();
	}

	public void start() {
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				cells[r][c] = EMPTY;

		gameState = GameState.PLAYING;
		currentPlayer = firstPlayer;
		
		moves = new Stack<Integer>();
	}

	public boolean move() {
		return move(-1, -1);
	}

	public boolean move(int row, int col) {
		//if the player the current player has run out of numbers to play then switch players.
		if(currentPlayer.numbers.isEmpty())
			currentPlayer = (currentPlayer == firstPlayer ? secondPlayer
					: firstPlayer);

		//both players shouldn't run out of numbers before a draw occurs.
		assert firstPlayer.numbers.isEmpty() && secondPlayer.numbers.isEmpty():("Should have been a draw if both ran out of numbers.");

		//make move
		if (currentPlayer.human) {
			if (row < 0 || col < 0 || row > 2 || col > 2
					|| cells[row][col] != EMPTY)
				return false;
		} else { // computer player
			int pos = currentPlayer.move();
			col = pos % 3;
			row = pos / 3;
		}

		//there should never be a time where a player can use the same number because it has been removed from their list.
		assert usedNumbers.contains(currentPlayer.currentNumber):("This number should have been removed") ;//don't allow same number to be played twice.

		//can see if the player went or not,if size of the numbers list is still the same they never made a move.
		int currentSize = currentPlayer.numbers.size();

		System.out.println(" " + currentPlayer.human + " " + row + " " + col);
		// store move
		cells[row][col] = currentPlayer.currentNumber;		//change to the current players number.

		//need to remove placed number from the players list of numbers here 
		//and add it to a used numbers list,to avoid reusing already placed numbers.
		if(currentPlayer.currentNumber != 0){
			usedNumbers.add(currentPlayer.currentNumber);
			currentPlayer.removeUsedNumber(currentPlayer.currentNumber);

			//add players move to the stack
			moves.push(row*3+col);
			System.out.println("Positions players have played\n" + moves);
		}
		
		//show players numbers to choose from.
		System.out.println(currentPlayer.numbers);

		//show used numbers
		System.out.println("Used Numbers" + usedNumbers);
		System.out.print("Board:\n");
		for (int r=0; r<3; r++)
			for (int c=0; c<3; c++){
				System.out.print(" " + cells[r][c]);
				if(c==2)System.out.println();
			}
		System.out.println();

		//check win or draw.
		if (hasWon(currentPlayer.currentNumber, row, col)) {	//take in the number just placed.
			gameState = (currentPlayer.mySymbol == playerSymbol.EVEN) ? GameState.EVEN_WON
					: GameState.ODD_WON;
			AudioManager.instance.play(Assets.instance.sounds.win);
		} else if (isDraw()) {
			gameState = GameState.DRAW;
			AudioManager.instance.play(Assets.instance.sounds.draw);
		}

		//reset current players number to 0
		currentPlayer.currentNumber = 0;

		//if player hasn't gone yet don't switch
		if(currentPlayer.numbers.size() == currentSize)return false;

		// switch player 
		if (gameState == GameState.PLAYING ) {
			currentPlayer = (currentPlayer == firstPlayer ? secondPlayer
					: firstPlayer);
		}

		//update move (used for strategic moves in ImpactSpacePlayer)
		currentMove++;

		//get sound to play depending on player.
		Sound soundToPlay = (currentPlayer.mySymbol == playerSymbol.EVEN) ? 
				Assets.instance.sounds.first:Assets.instance.sounds.second;

		AudioManager.instance.play(soundToPlay);
		return true;
	}

	public boolean isDraw() {
		for (int r = 0; r < 3; ++r) {
			for (int c = 0; c < 3; ++c) {
				if (cells[r][c] == EMPTY) {
					return false; // an empty cell found, not a draw, exit
				}
			}
		}
		return true; // no empty cell, it's a draw
	}

	//checks to see if the numbers add up to 15 (GOAL_NUMBER)
	//and makes sure all numbers in the row are not empty. (i.e need 3 numbers to win)
	public boolean hasWon(int number, int row, int col) {
		return (
				// 3-in-the-row
				cells[row][0] + cells[row][1] + cells[row][2] == Constants.GOAL_NUMBER &&
				notEmpty(cells[row][0],cells[row][1],cells[row][2])
				||  // 3-in-the-column
				cells[0][col] + cells[1][col] + cells[2][col] == Constants.GOAL_NUMBER &&
				notEmpty(cells[0][col],cells[1][col],cells[2][col])
				||  // 3-in-the-diagonal
				cells[0][0] + cells[1][1] + cells[2][2] == Constants.GOAL_NUMBER &&
				notEmpty(cells[0][0],cells[1][1],cells[2][2])
				|| // 3-in-the-opposite-diagonal
				cells[0][2] + cells[1][1] + cells[2][0] == Constants.GOAL_NUMBER &&
				notEmpty(cells[0][2],cells[1][1],cells[2][0])
				);
	}

	//takes 3 spaces(numbers on the board) and makes sure they are not EMPTY i,e 0.
	public boolean notEmpty(int number1,int number2,int number3){
		if(number1 == EMPTY)return false;
		else if(number2 == EMPTY)return false;
		else if(number3 == EMPTY)return false;
		else return true;
	}

	//true if entire board is empty,false otherwise.
	public boolean allEmpty(){
		for (int r = 0; r < 3;r++) {
			for (int c = 0; c < 3; c++) {
				if (cells[r][c] != EMPTY) {
					return false; // an occupied cell found, not an empty board, exit
				}
			}
		}
		return true;
	}

	//returns true if the board contains the number we are looking for.
	public boolean contains(int number){
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (cells[r][c] == number) {
					return true; // an occupied cell found, not an empty board, exit
				}
			}
		}
		return false;
	}

	//returns the position on the board of the specified number.
	public int positionOf(int number){
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (cells[r][c] == number) {
					return r*3+c; // return position of number
				}
			}
		}
		return -1;
	}

	public void render(SpriteBatch batch) {
		TextureRegion region = Assets.instance.board.region;
		batch.draw(region.getTexture(), -2,
				-Constants.VIEWPORT_HEIGHT / 2 + 0.1f, 0, 0, 4, 4, 1, 1, 0,
				region.getRegionX(), region.getRegionY(),
				region.getRegionWidth(), region.getRegionHeight(), false, false);

		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				if (cells[row][col] == EMPTY) continue;
				if(cells[row][col] == one) region = Assets.instance.one.region;
				else if(cells[row][col] == two) region = Assets.instance.two.region;
				else if(cells[row][col] == three) region = Assets.instance.three.region;
				else if(cells[row][col] == four) region = Assets.instance.four.region;
				else if(cells[row][col] == five) region = Assets.instance.five.region;
				else if(cells[row][col] == six) region = Assets.instance.six.region;
				else if(cells[row][col] == seven) region = Assets.instance.seven.region;
				else if(cells[row][col] == eight) region = Assets.instance.eight.region;
				else if(cells[row][col] == nine) region = Assets.instance.nine.region;

				batch.draw(region.getTexture(), col*1.4f-1.9f,
						row*1.4f-2.3f, 0, 0, 1, 1, 1, 1, 0,
						region.getRegionX(), region.getRegionY(),
						region.getRegionWidth(), region.getRegionHeight(),
						false, false);
			}

		// draw drag and drop pieces
		region =  Assets.instance.one.region;
		batch.draw(region.getTexture(), (-1) * 1.4f - (-3.9f), 1 * 1.4f - .1f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.two.region;
		batch.draw(region.getTexture(), (3) * 1.4f - 8.2f, 1 * 1.4f - .1f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.three.region;
		batch.draw(region.getTexture(), (3) * 1.4f - (1f), 1 * 1.4f - 1.1f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.four.region;
		batch.draw(region.getTexture(), (3) * 1.4f - 8f, 1 * 1.4f - 1.2f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.five.region;
		batch.draw(region.getTexture(), (3) * 1.4f - (1.9f), 1 * 1.4f - 2.1f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.six.region;
		batch.draw(region.getTexture(), (3) * 1.4f - 8.2f, 1 * 1.4f - 2.4f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.seven.region;
		batch.draw(region.getTexture(), (3) * 1.4f - (1f), 1 * 1.4f - 3.1f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.eight.region;
		batch.draw(region.getTexture(), (3) * 1.4f - 8f, 1 * 1.4f - 3.6f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);
		region =  Assets.instance.nine.region;
		batch.draw(region.getTexture(), (3) * 1.4f - (2f), 1 * 1.4f - 4f, 0, 0, 1, 1, 1, 1, 0,
				region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(),
				false, false);

	}

}



package wit.cgd.xando.game.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.Board.Symbol;

public class MinimaxPlayer extends BasePlayer {

	private Random randomGenerator;
	//change constructor to take skill levels for depth search.
	public MinimaxPlayer(Board board, Symbol symbol,int depth) {
		super(board, symbol);
		name = "MinimaxPlayer";

		// skill is measure of search depth
		skill = depth;
		System.out.println("Skill level......" + skill);

		randomGenerator = new Random();
	}

	@Override
	public int move () {
		return (board.currentPlayer == board.firstPlayer) ? 
				(int) minimax(board.currentPlayer.numbers, board.secondPlayer.numbers, 0) : (int) minimax(board.currentPlayer.numbers, board.firstPlayer.numbers, 0);
	}

	private float minimax(List<Integer> myNumbers, List<Integer> opponentNumbers, int depth) {

		final float WIN_SCORE = 100;        
		final float DRAW_SCORE = 0;

		float score;
		float maxScore = -10000;
		int maxPos = -1;

		// for each board position
		for (int r=0; r<3; ++r) {
			for (int c=0; c<3; ++c) {

				// skip over used positions
				if (board.cells[r][c]!=board.EMPTY)continue;

				String indent = new String(new char[depth]).replace("\0", "  ");
				//Gdx.app.log(indent, "search ("+r+","+c+")");
				for(Integer number : myNumbers){
					// place move 
					board.cells[r][c] = number;

					// evaluate board (recursively)
					if (board.hasWon(number, r, c)) {
						currentNumber = number;
						score = WIN_SCORE;
					} else if (board.isDraw()) {
						currentNumber = number;
						score = DRAW_SCORE;
					} else {
						currentNumber = number;
						if (depth<skill) {
							score = -minimax(opponentNumbers, myNumbers, depth+1);
						} else {
							score = 0;
						}
					}

					// update ranking

					if (Math.abs(score-maxScore)<1.0E-5 && randomGenerator.nextDouble()<0.1) {
						currentNumber = number;
						maxScore = score;
						maxPos = 3*r+c;

					} else if (score>maxScore) {    // clear 
						currentNumber = number;
						maxScore = score;
						maxPos = 3*r+c;
					} 

					// undo move 
					board.cells[r][c] = board.EMPTY;
				}
			}
		}
		// on uppermost call return move not score
		return (depth==0 ? maxPos : maxScore);
	};

}

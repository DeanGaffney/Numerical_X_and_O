package wit.cgd.xando.game.ai;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer.Random;

import wit.cgd.xando.game.BasePlayer;
import wit.cgd.xando.game.Board;
import wit.cgd.xando.game.Board.Symbol;

public class ImpactSpacePlayer extends BasePlayer {

	public ImpactSpacePlayer(Board board, Symbol symbol) {
		super(board, symbol);
		name = "ImpactSpacePlayer";
	}


	@Override
	public int move() {
		assert board.isDraw():"Shouldn't be here";
		/*
		 * if odd has played a 5 and we are even and its our turn on the third go
		 * we should try and go for the double threat if the board contains an available position.
		 */
		if(board.currentMove == 3 && board.contains(5) && board.currentPlayer.mySymbol == board.playerSymbol.EVEN){
			currentNumber = strategicMove();
			return bestPositionForEven();
		}
		//if board is empty and it's odds turn (first turn) then make the strategic move (i.e dont play 5).
		else if(board.allEmpty() && board.currentPlayer.mySymbol == board.playerSymbol.ODD){
			for(int r=0;r < 3; r++){
				for(int c=0; c < 3; c++){
					if(board.cells[r][c] == board.EMPTY){
						currentNumber = strategicMove();
						return r*3+c;
					}
				}
			}
		}else{							//we should follow these guidelines if above check is false.
			//check your win
			for(int r=0;r < 3; r++){
				for(int c=0; c < 3; c++){
					if(board.cells[r][c] == board.EMPTY && isWinningMove(board.currentPlayer,r,c)){
						return r*3+c;
					}
				}
			}

			//check opponent win and block
			for(int r=0;r < 3; r++){
				for(int c=0; c < 3; c++){
					if(board.cells[r][c] == board.EMPTY && isWinningMove(getOpponentPlayer(),r,c)){
						return r*3+c;
					}
				}
			}
			
			//make random move with random number if no other options.
			while(true){
				int r = (int)(Math.random() * 3);
				int c = (int)(Math.random() * 3);
				if(board.cells[r][c] == board.EMPTY){
					currentNumber = getRandomNumber();
					return r*3+c;
				}
			}
		}
		return -1;
	}


	//called if its the first move of the game for this player to place a strategy move..
	public int strategicMove(){
		//filter out 5 for the first move and choose a random number from the safe numbers list.(Deals with player being odd)
		if(board.currentPlayer.mySymbol == board.playerSymbol.ODD){
			List<Integer> safeNumbers = numbers.stream()
					.filter(num->num != 5)
					.collect(Collectors.toList());

			//pick a random number from the safe numbers list.
			return safeNumbers.get((int) (Math.random() * safeNumbers.size()));

		}else{ //player must be even so approach first move as even.
			List<Integer> bestNumbers = numbers.stream()
					.filter(num-> num != 8)
					.collect(Collectors.toList());	//limits to 2,4,6(pick one randomly and handle best case in move)
			return bestNumbers.get((int) (Math.random() * bestNumbers.size()));
		}
	}

	//called if the player is EVEN to find best place if the board contains a 5.called if a bestposition for even is free
	public int bestPositionForEven(){
		int position = board.positionOf(5);
		//i want a position beside 5 or below 5.
		int left = position + 1;		//gets left space
		int right = position - 1;	//gets right space
		int bottomBelow = position - 6;	//gets space bottom below
		int topAbove = position + 6;	//gets space top above.
		int directlyBelow = position - 3; //space directly below
		int directlyAbove = position + 3; //directly above

		if(left >= 0 && left <= 8 && board.cells[left/3][left%3] == board.EMPTY)return left;
		else if (right >= 0 && right <= 8 && board.cells[right/3][right%3] == board.EMPTY) return right;
		else if (bottomBelow >= 0 && bottomBelow <=8 && board.cells[bottomBelow/3][bottomBelow%3] == board.EMPTY)return bottomBelow;
		else if(topAbove >=0 && topAbove <= 8 && board.cells[topAbove/3][topAbove%3] == board.EMPTY)return topAbove;
		else if(directlyBelow >= 0 && directlyBelow <= 8 && board.cells[directlyBelow/3][directlyBelow%3] == board.EMPTY)return directlyBelow;
		else if(directlyAbove >= 0 && directlyAbove <= 8 && board.cells[directlyAbove/3][directlyAbove%3] == board.EMPTY)return directlyAbove;
		else return -1;
	}

	//returns the true if position given wins the game,arguments are swapped between currentPlayer and opponent.
	public boolean isWinningMove(BasePlayer player,int row,int col){
		//see what player we are dealing with to use your own winning number or a random number.
		boolean ownNumber = (player.mySymbol == board.currentPlayer.mySymbol) ? true:false;

		//go for win with your current symbol,if its your player use winning num,otherwise block with random num.
		for(Integer number : player.numbers){
			board.cells[row][col] = number;
			if(board.hasWon(number, row, col)){
				board.cells[row][col] = board.EMPTY;
				currentNumber = (ownNumber) ? number : getRandomNumber();
				return true;
			}
		}
		board.cells[row][col] = board.EMPTY;
		return false;
	}

	//returns the opposing player (i.e for numbers access)
	public BasePlayer getOpponentPlayer(){
		return (board.currentPlayer == board.firstPlayer) ? board.secondPlayer : board.firstPlayer;
	}

	//returns a random number.
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

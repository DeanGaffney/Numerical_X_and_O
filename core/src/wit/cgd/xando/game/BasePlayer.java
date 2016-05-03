package wit.cgd.xando.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import wit.cgd.xando.game.Board.Symbol;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class BasePlayer {
	public boolean human;
	public String name;
	public Board board;
	public int 	skill;
	public List<Integer>numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);	//each player should have a list of symbols (even or odd)
	public Symbol mySymbol, opponentSymbol;
	public int currentNumber;
	
	public BasePlayer(Board board,Symbol symbol) {
		this.board = board;
		setSymbol(symbol);
		
		human = false;
		currentNumber = 0;	//when initialised make current number 0 . (change to number to be placed as game goes on)
	}
	
	public void setSymbol(Symbol symbol) {
		mySymbol = symbol;
		opponentSymbol = (symbol == board.playerSymbol.EVEN) ? board.playerSymbol.ODD : board.playerSymbol.EVEN;
		
		//when the symbol has been picked change the symbols array to match even or odd numbers.
		if(symbol == board.playerSymbol.EVEN){
			this.numbers= numbers.stream()		//create even number list for player upon creation.
					.filter(even->even % 2 == 0)
					.collect(Collectors.toList());
		}else{
			this.numbers= numbers.stream()		//create odd number list for player upon creation.
					.filter(odd->odd % 2 != 0)
					.collect(Collectors.toList());
		}
	}

	//sets the current number depending on the region which has been placed.
	//(For human use,has to be placed here for current player use.)
	public void setCurrentNumber(TextureRegion region,Symbol playerSymbol){
		if(playerSymbol == board.playerSymbol.EVEN){
			if(region == Assets.instance.two.region)currentNumber = 2;
			else if(region == Assets.instance.four.region)currentNumber = 4;
			else if(region == Assets.instance.six.region)currentNumber = 6;
			else if(region == Assets.instance.eight.region)currentNumber = 8;
		}else{
			if(region == Assets.instance.one.region)currentNumber = 1;
			else if(region == Assets.instance.three.region)currentNumber = 3;
			else if(region == Assets.instance.five.region)currentNumber = 5;
			else if(region == Assets.instance.seven.region)currentNumber = 7;
			else if(region == Assets.instance.nine.region)currentNumber = 9;
			else currentNumber = 0;
			}
	}
	
	public void removeUsedNumber(int numberToRemove){
		for(int i = 0; i < numbers.size();i++){
			if(numbers.get(i) == numberToRemove)numbers.remove(i);
		}
	}
	
	public boolean checkValidNumber(int number){
		return numbers.contains(number);
	}
			
	public abstract int move ();
	
}

package wit.cgd.xando.game;

import wit.cgd.xando.game.Board.Symbol;

public class HumanPlayer extends BasePlayer {
	
	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName(); 
	
	public HumanPlayer(Board board, Symbol symbol) {
		super(board, symbol);
		human = true;
		name = "Human";
		System.out.println("\nPlayer numbers");		//DEBUG PURPOSES
		for(Integer num : numbers)System.out.print(num + " ");
	}

	@Override
	public int move() {
		// human move handled in worldController
		return 0;
	}
}

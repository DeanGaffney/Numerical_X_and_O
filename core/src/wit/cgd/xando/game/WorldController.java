package wit.cgd.xando.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import wit.cgd.xando.game.ai.ImpactSpacePlayer;
import wit.cgd.xando.game.util.GamePreferences;
import wit.cgd.xando.game.util.GameStats;
import wit.cgd.xando.screens.MenuScreen;

public class WorldController extends InputAdapter {

	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName(); 

	public float 				viewportWidth;
	public int 					width, height;
	public Board 				board;
	float						timeLeftGameOverDelay;
	boolean                     dragging = false;
	int                         dragX, dragY;
	TextureRegion               dragRegion;
	private Game				game;
	private GamePreferences		prefs = GamePreferences.instance;
	
	public boolean 					undoButtonClicked;

	public WorldController(Game game) {
		this.game = game;
		init();
	}

	private void init() {
		//input processor handled in GameScreen using Multiplexer.
		board = new Board();
		if(prefs.firstPlayerHuman){
			board.firstPlayer = new HumanPlayer(board,board.playerSymbol.EVEN);
		}
		else{
			board.firstPlayer = new ImpactSpacePlayer(board, board.playerSymbol.EVEN);
		}
		if(prefs.secondPlayerHuman){
			board.secondPlayer = new HumanPlayer(board, board.playerSymbol.ODD);
		}
		else{
			board.secondPlayer = new ImpactSpacePlayer(board, board.playerSymbol.ODD);
		}
	
		board.firstPlayer.opponent = board.secondPlayer;
		board.secondPlayer.opponent = board.firstPlayer;
		board.start();
		undoButtonClicked = false;

		timeLeftGameOverDelay = 2;
	}

	public void update(float deltaTime) {
		if (board.gameState == Board.GameState.PLAYING) {
			board.move();
		}

		if (board.gameState != Board.GameState.PLAYING) {
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) {
				if (board.gameState == Board.GameState.EVEN_WON) {
					GameStats.instance.win();
				} else if (board.gameState== Board.GameState.ODD_WON) {
					GameStats.instance.lose();
				} else {
					GameStats.instance.draw();
				}
				backToMenu();
			}
		}

	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			backToMenu();
		}
		return false;
	}
	
	//returns if the undo button has been clicked on the game screen.
	public boolean isUndoClicked(){
		return undoButtonClicked;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (board.gameState == Board.GameState.PLAYING && board.currentPlayer.human) {

			// convert to cell position
			int row = 4 * (height - screenY) / height;
			int col = (int) (viewportWidth * (screenX - 0.5 * width) / width) + 1;

			// board move - just place piece and return
			if (row >= 0 && row < 3 && col >= 0 && col < 3) {
				board.move(row, col);
				return true;
			}

			dragX = screenX;
			dragY = screenY;
			
			//set appropriate drag and drop checks,and checks that number hasn't already been used.
			if (row == 3 && col == 3 && board.currentPlayer==board.secondPlayer) {
				if(board.currentPlayer.checkValidNumber(1)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.one.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					Assets.instance.one.used = true;
				}
				return true;
			}
			if (row == 3 && col == -2 && board.currentPlayer==board.firstPlayer) {
				if(board.currentPlayer.checkValidNumber(2)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.two.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}				
				return true;
			}
			if (row == 2 && col == 4 && board.currentPlayer==board.secondPlayer) {
				if(board.currentPlayer.checkValidNumber(3)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.three.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}		
				return true;
			}
			if (row == 2 && col == -2 && board.currentPlayer==board.firstPlayer) {
				if(board.currentPlayer.checkValidNumber(4)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.four.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}					
				return true;
			}
			if (row == 1 && col == 3 && board.currentPlayer==board.secondPlayer) {
				if(board.currentPlayer.checkValidNumber(5)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.five.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}					
				return true;
			}
			if (row == 1 && col == -2 && board.currentPlayer==board.firstPlayer) {
				if(board.currentPlayer.checkValidNumber(6)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.six.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}				
				return true;
			}
			if (row == 0 && col == 4 && board.currentPlayer==board.secondPlayer) {
				if(board.currentPlayer.checkValidNumber(7)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.seven.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}				
				return true;
			}
			if (row == 0 && col == -2 && board.currentPlayer==board.firstPlayer) {
				if(board.currentPlayer.checkValidNumber(8)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.eight.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}					
				return true;
			}
			if (row == 0 && col == 3 && board.currentPlayer==board.secondPlayer) {
				if(board.currentPlayer.checkValidNumber(9)){//avoid pointless setting of number
					dragging = true;
					dragRegion = Assets.instance.nine.region;
					board.currentPlayer.setCurrentNumber(dragRegion,board.currentPlayer.mySymbol);
					}					
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		dragX = screenX;
		dragY = screenY;        
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		dragging = false;

		// convert to cell position
		int row = 4 * (height - screenY) / height;
		int col = (int) (viewportWidth * (screenX - 0.5 * width) / width) + 1;

		// if a valid board cell then place piece
		if (row >= 0 && row < 3 && col >= 0 && col < 3) {
			board.move(row, col);
			return true;
		}

		return true;
	}

	private void backToMenu() {
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}

}

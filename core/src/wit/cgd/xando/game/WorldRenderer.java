/**
 * @file        WorldRenderer.java
 * @author      Dean Gaffney 20067423
 * @assignment  Controls all the rendering within the game.
 * @brief      Controls all aspects of rendering the game.
 *
 * @notes       
 * 				
 */
package wit.cgd.xando.game;

import wit.cgd.xando.game.WorldController;
import wit.cgd.xando.game.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {

	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName(); 


	public OrthographicCamera	camera;
	public OrthographicCamera	cameraGUI;

	private SpriteBatch			batch;
	private WorldController		worldController;
	public boolean				renderWinHint;
	public boolean 				renderBlockHint;
	public boolean				renderRandomHint;
	public float 				renderHintTime;
	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}

	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip y-axis
		cameraGUI.update();
		renderWinHint = false;
		renderBlockHint = false;
		renderRandomHint = false;
		renderHintTime = 3.0f;
	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height) * (float) width;
		camera.update();
		worldController.viewportWidth = camera.viewportWidth;
		worldController.width = width;
		worldController.height = height;
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}

	public void render() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.board.render(batch);

		if (worldController.dragging) {

			float x = worldController.dragX;
			x = (float) (worldController.viewportWidth * (x - 0.5 * worldController.width) / worldController.width);

			float y = worldController.dragY;
			y = (float) (4.0 * (worldController.height - y) / worldController.height-2.5);

			batch.draw(worldController.dragRegion.getTexture(), 
					x, y, 0, 0, 1, 1, 1, 1, 0,
					worldController.dragRegion.getRegionX(), worldController.dragRegion.getRegionY(), 
					worldController.dragRegion.getRegionWidth(), worldController.dragRegion.getRegionHeight(),
					false, false);
		}

		batch.end();


		// GUI rendering

		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		if (worldController.board.gameState != Board.GameState.PLAYING) {
			float x = cameraGUI.viewportWidth / 2;
			float y = cameraGUI.viewportHeight / 2;
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			String message = "EVEN WON";
			if (worldController.board.gameState== worldController.board.gameState.ODD_WON) 
				message = "ODD WON";
			else if (worldController.board.gameState== worldController.board.gameState.DRAW)
				message = "DRAW";
			fontGameOver.draw(batch, message, x, y, 0, Align.center, true);
			fontGameOver.setColor(1, 1, 1, 1);
		}
		
		//check if hints need to be rendered.
		if(renderWinHint && renderHintTime > 0){
			renderHintTime -= Gdx.graphics.getDeltaTime();
			if(renderHintTime <= 0){
				renderHintTime = 3.0f;
				renderWinHint = false;
			}else{
				float x = cameraGUI.viewportWidth / 2;
				float y = cameraGUI.viewportHeight / 2;
				BitmapFont fontWin = Assets.instance.fonts.defaultNormal;
				fontWin.setColor(1, 0.75f, 0.25f, 1);
				String message = "GO FOR THE WIN!";
				fontWin.draw(batch, message, x, y, 0, Align.center, true);
				fontWin.setColor(1, 1, 1, 1);
			}
		}
		if(renderBlockHint && renderHintTime > 0){
			renderHintTime -= Gdx.graphics.getDeltaTime();
			if(renderHintTime <= 0){
				renderHintTime = 3.0f;
				renderBlockHint = false;
			}else{
				float x = cameraGUI.viewportWidth / 2;
				float y = cameraGUI.viewportHeight / 2;
				BitmapFont fontWin = Assets.instance.fonts.defaultNormal;
				fontWin.setColor(1, 0.75f, 0.25f, 1);
				String message = "GO FOR THE BLOCK!";
				fontWin.draw(batch, message, x, y, 0, Align.center, true);
				fontWin.setColor(1, 1, 1, 1);
			}
		}
		if(renderRandomHint && renderHintTime > 0){
			renderHintTime -= Gdx.graphics.getDeltaTime();
			if(renderHintTime <= 0){
				renderHintTime = 3.0f;
				renderRandomHint = false;
			}else{
				float x = cameraGUI.viewportWidth / 2;
				float y = cameraGUI.viewportHeight / 2;
				BitmapFont fontWin = Assets.instance.fonts.defaultNormal;
				fontWin.setColor(1, 0.75f, 0.25f, 1);
				String message = "GO FOR A RANDOM MOVE!";
				fontWin.draw(batch, message, x, y, 0, Align.center, true);
				fontWin.setColor(1, 1, 1, 1);
			}
		}
		batch.end();
	}


	@Override public void dispose() {
		batch.dispose();
	}
}

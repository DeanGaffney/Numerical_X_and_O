/**
 * @file        NumericalXandO.java
 * @author      Dean Gaffney 20067423
 * @assignment  The main class for running the application.
 * @brief       This class controls all the setup and running of the game.
 *
 * @notes      
 * 				
 */
package wit.cgd.xando;

import wit.cgd.xando.game.Assets;
import wit.cgd.xando.game.util.AudioManager;
import wit.cgd.xando.game.util.GamePreferences;
import wit.cgd.xando.screens.MenuScreen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumericalXandO extends Game {
	SpriteBatch batch;
	Texture img;
	@Override
    public void create() {
        // Set Libgdx log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        // Load assets
        Assets.instance.init(new AssetManager());
        // Start game at menu screen
        setScreen(new MenuScreen(this));
     // Load preferences for audio settings and start playing music
        GamePreferences.instance.load();
        AudioManager.instance.play(Assets.instance.music.song01);
    }
	
}

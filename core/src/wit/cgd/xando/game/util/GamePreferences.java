/**
 * @file        GamePreferences.java
 * @author      Dean Gaffney 20067423
 * @assignment  Stores all of the game preferences set by player.
 * @brief       
 *
 * @notes    
 */
package wit.cgd.xando.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class GamePreferences {

	public static final String          TAG         = GamePreferences.class.getName();

    public static final GamePreferences instance    = new GamePreferences();
    private Preferences                 prefs;
    public boolean 						firstPlayerHuman;
    public float 						firstPlayerSkill;
    public boolean 						secondPlayerHuman;
    public float						secondPlayerSkill;
    public boolean						sound;
    public float						soundVolume;
    public boolean						music;
    public float						musicVolume;
    
    private GamePreferences() {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public void load() {
    	firstPlayerHuman = prefs.getBoolean("firstPlayerHuman",true);
    	firstPlayerSkill = MathUtils.clamp(prefs.getFloat("firsPlayerSkill",0), 0, 10);
    	secondPlayerHuman = prefs.getBoolean("secondPlayerHuman",false);
    	secondPlayerSkill = MathUtils.clamp(prefs.getFloat("secondPlayerSkill",0), 0, 10);
    	
    	sound = prefs.getBoolean("sound");
    	soundVolume = MathUtils.clamp(prefs.getFloat("soundVolume",0),0,1);
    	music = prefs.getBoolean("music");
    	musicVolume = MathUtils.clamp(prefs.getFloat("musicVolume",0),0,1);
    }

    public void save() {
    	prefs.putBoolean("firstPlayerHuman", firstPlayerHuman);
		prefs.putFloat("firstPlayerSkill", firstPlayerSkill);
		prefs.putBoolean("secondPlayerHuman", secondPlayerHuman);
		prefs.putFloat("secondPlayerSkill", secondPlayerSkill);
		
    	prefs.putBoolean("sound", sound);
		prefs.putFloat("soundVolume", soundVolume);
    	prefs.putBoolean("music", music);
		prefs.putFloat("musicVolume", musicVolume);
        prefs.flush();
    }
    
}

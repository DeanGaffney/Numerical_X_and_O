/**
 * @file        Constants.java
 * @author      Dean Gaffney 20067423
 * @assignment  Stores all the games constant values.
 * @brief      
 *
 * @notes     
 * 				
 */
package wit.cgd.xando.game.util;

public class Constants {
	
    // Visible game world is 5 meters wide
    public static final float   VIEWPORT_WIDTH = 5.0f;
    // Visible game world is 5 meters tall
    public static final float   VIEWPORT_HEIGHT  = 5.0f;

	// GUI Width
	public static final float VIEWPORT_GUI_WIDTH = 800.0f;
	// GUI Height
	public static final float VIEWPORT_GUI_HEIGHT = 480.0f;

    public static final String  TEXTURE_ATLAS_OBJECTS = "images/xando.atlas";
    
    // location of game specific skin and atlas
    public static final String  SKIN_UI                 = "images/ui.json";
    public static final String  TEXTURE_ATLAS_UI        = "images/ui.atlas";

    // location of libgdx default skin and atlas
    public static final String  SKIN_LIBGDX_UI          = "images/uiskin.json";
    public static final String  TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas";
    
    public static final int BUTTON_PAD      = 5;
    
    public static final int GOAL_NUMBER		=15;
    
 // Game setting (preferences + stats) files
    public static final String STATS = "xando.stats";
    
    public static final String PREFERENCES = "xando.prefs";
}

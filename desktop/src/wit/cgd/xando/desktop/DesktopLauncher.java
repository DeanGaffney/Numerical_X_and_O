package wit.cgd.xando.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import wit.cgd.xando.NumericalXandO;

public class DesktopLauncher {
	private static boolean  rebuildAtlas        = false;
	private static boolean  drawDebugOutline    = false;
	
	public static void main (String[] arg) {
		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/images", "../android/assets/images",
					"xando.atlas");
			TexturePacker.process(settings, "assets-raw/images-ui", "../android/assets/images","ui.atlas");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Numerical X and O";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new NumericalXandO(), config);
	}
}

/**
 * @file        Assets.java
 * @author      Dean Gaffney 20067423
 * @assignment  Creates all assets for the game
 * @brief       This class controls all the oassets for the game
 *
 * @notes       
 * 				
 */
package wit.cgd.xando.game;

import wit.cgd.xando.game.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {

	@SuppressWarnings("unused")
	private static final String	TAG	= WorldRenderer.class.getName(); 

	public static final Assets instance = new Assets();
	private AssetManager assetManager;

	public AssetFonts 			fonts;
	public AssetSounds          sounds;
	public AssetMusic           music;
	public Asset 				board;
	public Asset				one;
	public Asset 				two;
	public Asset				three;
	public Asset 				four;
	public Asset				five;
	public Asset 				six;
	public Asset				seven;
	public Asset 				eight;
	public Asset				nine;

	public class Asset {
		public final AtlasRegion region;
		public boolean used;
		public Asset(TextureAtlas atlas, String imageName) {
			region = atlas.findRegion(imageName);
			used = false;
		}
	}

	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts () {
			// create three fonts using Libgdx's built-in 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(4.0f);

			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}


	private Assets() { }

	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		// start loading assets and wait until finished
		assetManager.finishLoading();

		Gdx.app.debug(TAG,
				"# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);

		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		fonts = new AssetFonts();

		// build game resource objects
		board = new Asset(atlas, "board");
		one = new Asset(atlas, "1");		
		two = new Asset(atlas, "2");
		three = new Asset(atlas, "3");
		four = new Asset(atlas, "4");
		five = new Asset(atlas, "5");
		six = new Asset(atlas, "6");
		seven = new Asset(atlas, "7");
		eight = new Asset(atlas, "8");
		nine = new Asset(atlas, "9");

		// load sounds
		assetManager.load("sounds/first.wav", Sound.class);
		assetManager.finishLoading();
		assetManager.load("sounds/second.wav", Sound.class);
		assetManager.finishLoading();
		assetManager.load("sounds/win.wav", Sound.class);
		assetManager.finishLoading();
		assetManager.load("sounds/draw.wav", Sound.class);
		assetManager.finishLoading();

		// load music
		assetManager.load("music/keith303_-_brand_new_highscore.mp3", Music.class);
		assetManager.finishLoading();

		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);
		
		
	}

	public class AssetSounds {

		public final Sound  first;
		public final Sound  second;
		public final Sound  win;
		public final Sound  draw;

		public AssetSounds(AssetManager am) {
			first = am.get("sounds/first.wav", Sound.class);
			second = am.get("sounds/second.wav", Sound.class);
			win = am.get("sounds/win.wav", Sound.class);
			draw = am.get("sounds/draw.wav", Sound.class);
		}
	}

	public class AssetMusic {
		public final Music  song01;

		public AssetMusic(AssetManager am) {
			song01 = am.get("music/keith303_-_brand_new_highscore.mp3", Music.class);
		}
	}


	@Override public void dispose() {
		assetManager.dispose(); 	
	}

	@Override public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset + "'", (Exception) throwable);
	}
}
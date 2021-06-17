package com.mygdx.game;


import Screens.BattleScreen;
import UI.SkinGenerator;
import Screens.*;
import Screens.transition.Transition;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import power.PowerDatabase;

public class MyGame extends Game {



	///Recursos a cargar

	private AssetManager assetManager;
	private Skin skin;
	private TweenManager tweenManager;

	/// Pantallas de juego
	private GameScreen gameScreen;
	private BattleScreen battleScreen;
	private TransitionScreen transitionScreen;
  private StartScreen startscreen;
  private HouseScreen houseScreen;

	///Poderes
	private PowerDatabase powerDatabase;


	// Metodos //


	@Override
	public void create () {


		/* Aca iria el tweening si es que lo usamos*/

		tweenManager = new TweenManager();

		/*Carga de recursos*/

		assetManager = new AssetManager();


		assetManager.load("PJ/player.atlas", TextureAtlas.class);
		assetManager.load("PJ/PixiPili.atlas",TextureAtlas.class);

		assetManager.load("font/small_letters_font.fnt", BitmapFont.class);
		assetManager.load("Ui/textBox.atlas", TextureAtlas.class);
		assetManager.load("PJ/pixiTito.atlas",TextureAtlas.class);
		assetManager.load("PJ/PixiDemi.atlas",TextureAtlas.class);
		assetManager.load("PJ/Enemigos.atlas",TextureAtlas.class);
		assetManager.load("res/graphics/stattusefect/white.png", Texture.class);
		assetManager.load("res/graphics/stattusefect/start.png", Texture.class);
		assetManager.finishLoading();
    


		/*Esto deberia ir en cada screen. sea battle, game, etc...*/



		/*INICIALIZA SKIN Y POWER DATABASE

		 */
		skin = SkinGenerator.generateSkin(assetManager);

		powerDatabase = new PowerDatabase();

		/*INICIALIZACION DE SCREENS*/

		gameScreen = new GameScreen(this);
    startscreen = new StartScreen(this);
    houseScreen = new HouseScreen(this);
		battleScreen = new BattleScreen(this);
		transitionScreen = new TransitionScreen(this);


		this.setScreen(startscreen);


	}


	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getScreen().render(Gdx.graphics.getDeltaTime());
	}

	public GameScreen getGameScreen() { return gameScreen; }

	public BattleScreen getBattleScreen() {
		return battleScreen;
	}
  
  public HouseScreen getHouseScreen(){
		return houseScreen;
	}



	public TweenManager getTweenManager() {
		return tweenManager;
	}



	public Skin getSkin() {
		return skin;
	}



	public void startTransition(AbstractScreen from, AbstractScreen to, Transition out, Transition in) { //chequear la interface de accion, creo que no hace falta
		transitionScreen.startTransition(from, to, out, in);
	}


	public PowerDatabase getPowerDatabase() {
		return powerDatabase;
	}


}

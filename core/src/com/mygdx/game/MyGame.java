package com.mygdx.game;

import Screeen.AbstractScreen;
import Screeen.BattleScreen;
import Screeen.GameScreen;
import Screeen.TransitionScreen;
import Screeen.transition.Transition;
import UI.SkinGenerator;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import power.PowerDatabase;
//import util.DialogueLoader;

public class MyGame extends Game {



	///Recursos a cargar

	private AssetManager assetManager;
	private Skin skin;
	private TweenManager tweenManager;

	/// Pantallas de juego
	private GameScreen gameScreen;
	private BattleScreen battleScreen;
	private TransitionScreen transitionScreen;

	///Poderes
	private PowerDatabase powerDatabase;

	///Caracteristicas de la camara/*
	private int widthScreen, heightScreen;
	private OrthographicCamera orthographicCamera;


	///Shaders para la transicion
	private ShaderProgram overlayShader;
	private ShaderProgram transitionShader;



	// Metodos //


	@Override
	public void create () {


		/* Carga de shaders*/

		/* Aca iria el tweening si es que lo usamos*/

		tweenManager = new TweenManager();

		/*Carga de recursos*/



		assetManager = new AssetManager();

		//assetManager.setLoader(DialogueDb.class,new DialogueLoader(new InternalFileHandleResolver()));

	//	assetManager.load("Dialogues.xml", DialogueDb.class);
		assetManager.load("Ui/uipack.atlas", TextureAtlas.class);
		assetManager.load("PJ/player.atlas", TextureAtlas.class);
		assetManager.load("PJ/PixiPili.atlas",TextureAtlas.class);
		assetManager.load("PJ/pj0.png",Texture.class);
		assetManager.load("res/graphics/stattusefect/white.png", Texture.class);
		assetManager.load("font/small_letters_font.fnt", BitmapFont.class);
		assetManager.load("Ui/textBox.atlas", TextureAtlas.class);
		assetManager.finishLoading();


		/*Carga de "Skins" (para los dialogos)*/
		///cerar clase skin


		/*Esto deberia ir en cada screen. sea battle, game, etc...*/



		/*INICIALIZA SKIN Y POWER DATABASE

		 */
		skin = SkinGenerator.generateSkin(assetManager);

		powerDatabase = new PowerDatabase();

		/*INICIALIZACION DE SCREENS*/

		gameScreen = new GameScreen(this);
		battleScreen = new BattleScreen(this);
		transitionScreen = new TransitionScreen(this);


		this.setScreen(battleScreen);



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

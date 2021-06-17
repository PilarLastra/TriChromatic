package com.mygdx.game;

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

public class MyGame extends Game {

	///Recursos a cargar

	private AssetManager assetManager;
	private TweenManager tweenManager;

	/// Pantallas de juego
	private GameScreen gameScreen;
	private HouseScreen houseScreen;
	private TransitionScreen transitionScreen;
	private StartScreen startscreen;

	// Metodos //


	@Override
	public void create () {

		/* Aca iria el tweening si es que lo usamos*/

		tweenManager = new TweenManager();

		/*Carga de recursos*/

		assetManager = new AssetManager();
		assetManager.load("PJ/player.atlas", TextureAtlas.class);
		assetManager.load("PJ/PixiPili.atlas",TextureAtlas.class);
		assetManager.load("PJ/pixiTito.atlas",TextureAtlas.class);
		assetManager.load("PJ/PixiDemi.atlas",TextureAtlas.class);
		assetManager.load("PJ/Enemigos.atlas",TextureAtlas.class);
		assetManager.load("res/graphics/stattusefect/white.png", Texture.class);
		assetManager.load("res/graphics/stattusefect/start.png", Texture.class);
		assetManager.finishLoading();


		/*INICIALIZACION DE SCREENS*/

		gameScreen = new GameScreen(this);

		houseScreen = new HouseScreen(this);

		transitionScreen = new TransitionScreen(this);

		startscreen = new StartScreen(this);


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

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public HouseScreen getHouseScreen(){
		return houseScreen;
	}

	public TweenManager getTweenManager() {
		return tweenManager;
	}

	public void startTransition(AbstractScreen from, AbstractScreen to, Transition out, Transition in) { //chequear la interface de accion, creo que no hace falta
		transitionScreen.startTransition(from, to, out, in);
	}


}

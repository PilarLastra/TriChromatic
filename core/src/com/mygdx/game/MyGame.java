package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.ScreenUtils;



public class MyGame extends Game {

	public static MyGame INSTANCE;

	private int widthScreen, heightScreen;

	private OrthographicCamera orthographicCamera, putos;

	private AssetManager assetManager;

	private GameScreen screen;




	// Metodos //
	public MyGame(){
		INSTANCE = this;
	}

	@Override
	public void create () {
		assetManager = new AssetManager();
		assetManager.load("PJ/player.atlas", TextureAtlas.class);
		assetManager.load("PJ/PixiPili.atlas",TextureAtlas.class);
		assetManager.finishLoading();

		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.putos = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		setScreen(screen = new GameScreen(orthographicCamera));



	}

	public void screenPrincipa ()
	{
		setScreen(screen);
	}

	public void newScreen (){
		setScreen(new GameScreen(putos));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}


	@Override
	public void render() {

	}

	@Override
	public void dispose () {

	}
}

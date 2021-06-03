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
	private OrthographicCamera orthographicCamera;

	private AssetManager assetManager;


	// Metodos //
	public MyGame(){
		INSTANCE = this;
	}

	@Override
	public void create () {
		assetManager = new AssetManager();
		assetManager.load("PJ/player.atlas", TextureAtlas.class);
		assetManager.finishLoading();

		this.widthScreen = Gdx.graphics.getWidth();
		this.heightScreen = Gdx.graphics.getHeight();
		this.orthographicCamera = new OrthographicCamera();
		this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
		setScreen(new GameScreen(orthographicCamera));


	}

	public AssetManager getAssetManager() {
		return assetManager;
	}



	@Override
	public void dispose () {

	}
}

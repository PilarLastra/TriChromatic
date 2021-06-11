package com.mygdx.game;


import com.badlogic.gdx.ScreenAdapter;

public abstract class AbstractScreen extends ScreenAdapter {

    private MyGame app;

    public AbstractScreen(MyGame app) {
        this.app = app;
    }

    @Override
    public abstract void dispose();

    @Override
    public abstract void hide();

    @Override
    public abstract void pause();

    public abstract void update(float delta);

    @Override
    public abstract void render(float delta);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void resume();

    @Override
    public abstract void show();

    public PokemonGame getApp() {
        return app;
    }
}

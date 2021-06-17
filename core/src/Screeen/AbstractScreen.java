package Screeen;



import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.MyGame;

public abstract class AbstractScreen extends ScreenAdapter {

    private MyGame app;

    public AbstractScreen(MyGame app) {
        this.app = app;
    }


    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    public abstract void update(float delta);

    @Override
    public void show() {

    }

    @Override
    public void hide() {
        ;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public MyGame getApp() {
        return app;
    }
}

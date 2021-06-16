package Screens;

import Screens.transition.FadeInTransition;
import Screens.transition.FadeOutTransition;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;

import static helper.Constante.PPM;

public class StartScreen extends AbstractScreen{

    private Batch batch;
    private Texture cartel;

    public StartScreen(MyGame app) {
        super(app);

        batch = new SpriteBatch();

        cartel = new Texture("res/graphics/stattusefect/start.png");
    }

    @Override
    public void update(float delta) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            fading();
        }

    }

    @Override
    public void render(float delta) {

        update(Gdx.graphics.getDeltaTime());

        batch.begin();
        batch.draw(cartel, 160, 0, 500, 500);
        batch.end();

    }

    public void fading() {
        getApp().startTransition(
                this,
                getApp().getHouseScreen(),
                new FadeOutTransition(1,  getApp().getTweenManager(), getApp().getAssetManager(), 0),
                new FadeInTransition(1,  getApp().getTweenManager(), getApp().getAssetManager(), 0));
    }
}

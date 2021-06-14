package Screens.transition;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FadeInTransition extends Transition{


    private Texture white;

    public FadeInTransition(float duration,  TweenManager tweenManager, AssetManager assetManager) {
        super(duration, tweenManager, assetManager);

        white = assetManager.get("res/graphics/stattusefect/white.png", Texture.class);
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        batch.begin();
        batch.setColor(1, 1, 1, (1-getProgress()));
        batch.draw(white, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();

        update(delta);
    }
}

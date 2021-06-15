package Screens.transition;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FadeOutTransition extends Transition{

    private Texture white;
    private int color; // 1=Blanco 0=Negro

    public FadeOutTransition(float duration, TweenManager tweenManager, AssetManager assetManager, int color) {
        super(duration, tweenManager, assetManager);
        this.color = color;
        white = assetManager.get("res/graphics/stattusefect/white.png", Texture.class);
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        batch.begin();
        batch.setColor(color, color, color, getProgress());
        batch.draw(white, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();
        update(delta);
    }


}

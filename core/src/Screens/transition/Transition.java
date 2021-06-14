package Screens.transition;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Transition {

    private float timer;
    private float duration;

    private boolean isFinished = false;

    private TweenManager tweenManager;
    private AssetManager assetManager;

    public Transition(float duration, TweenManager tweenManager, AssetManager assetManager) {
        this.duration = duration;
        this.timer = 0f;
        this.tweenManager = tweenManager;
        this.assetManager = assetManager;
    }

    public void update(float delta) {
        timer += delta;

        if (timer > duration) {
            isFinished = true;

        }
    }


    public abstract void render(float delta, SpriteBatch batch);

    public boolean isFinished() {
        return isFinished;
    }

    public float getProgress() {
        return (timer/duration);
    }

    public float getDuration() {
        return duration;
    }
}

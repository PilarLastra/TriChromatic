package Screens;

import Screens.transition.Transition;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;


public class TransitionScreen extends AbstractScreen{

    private AbstractScreen from;
    private AbstractScreen to;

    private Screens.transition.Transition outTransition;
    private Screens.transition.Transition inTransition;


    private SpriteBatch batch;
    private ScreenViewport viewport;
    private OrthographicCamera orthographicCamera;
    private float widthScreen, heightScreen;

    private TRANSITION_STATE state;

    private enum TRANSITION_STATE {
        OUT,
        IN,
        ;
    }

    public TransitionScreen(MyGame app) {
        super(app);
        batch = new SpriteBatch();
       /*orthographicCamera = new OrthographicCamera();
        widthScreen = Gdx.graphics.getWidth();
        heightScreen = Gdx.graphics.getHeight();
        orthographicCamera.setToOrtho(false,widthScreen,heightScreen);

        */

        viewport = new ScreenViewport();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
        if (state == TRANSITION_STATE.OUT) {
            outTransition.update(delta);
            if (outTransition.isFinished()) {
                // action.action();
                state = TRANSITION_STATE.IN;
                return;
            }
        } else if (state == TRANSITION_STATE.IN) {
            inTransition.update(delta);
            if (inTransition.isFinished()) {
                getApp().setScreen(to);
            }
        }
    }

    @Override
    public void render(float delta) {
        if (state == TRANSITION_STATE.OUT) {
            from.render(delta);

            viewport.apply();

            outTransition.render(delta, batch);
            update(delta);
        } else if (state == TRANSITION_STATE.IN) {
            to.render(delta);

            viewport.apply();
            inTransition.render(delta, batch);
            update(delta);
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        to.resize(width, height);
        from.resize(width, height);
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    public void startTransition(AbstractScreen from, AbstractScreen to, Screens.transition.Transition out, Transition in) {
        this.from = from;
        this.to = to;
        this.outTransition = out;
        this.inTransition = in;
        // this.action = action;
        this.state = TRANSITION_STATE.OUT;
        getApp().setScreen(this);

    }
}

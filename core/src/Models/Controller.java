package Models;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class Controller extends InputAdapter {

    private Actor actor;

    public Controller(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            actor.move(0,1);

        }
        if(keycode == Input.Keys.S){
            actor.move(0,-1);

        }
        if(keycode == Input.Keys.D){
            actor.move(1,0);

        }
        if(keycode == Input.Keys.A){
            actor.move(-1,0);

        }

        return false;
    }
}

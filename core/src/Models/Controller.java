package Models;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

//Cuando se actualize el game screen tmb se actualizara el controller y decidira que hacer en base a los booleanos up down left right
public class Controller extends InputAdapter {

    private Actor actor;

    private boolean up, down, right, left;

    public Controller(Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            up = true;

        }
        if(keycode == Input.Keys.S){
            down = true;

        }
        if(keycode == Input.Keys.D){
            right = true;

        }
        if(keycode == Input.Keys.A){
           left = true;

        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W){
            up = false;

        }
        if(keycode == Input.Keys.S){
            down = false;

        }
        if(keycode == Input.Keys.D){
            right = false;

        }
        if(keycode == Input.Keys.A){
            left = false;

        }

        return false;

    }

    //Va a ir actualizando los frames segun la la direccion
    public void update (float delta){

        if (up){
            actor.move(Direction.NORTH);
            return;
        }
        if (down){
            actor.move(Direction.SOUTH);
            return;
        }
        if (left){
            actor.move(Direction.WEST);
            return;
        }
        if (right){
            actor.move(Direction.EAST);
            return;
        }


    }










}

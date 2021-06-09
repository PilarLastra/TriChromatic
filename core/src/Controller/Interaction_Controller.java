package Controller;

import Models.Direction;
import Models.actor.Actor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.ArrayList;
import java.util.List;


public class Interaction_Controller extends InputAdapter {

    private Actor player;

    private Body bodyPJ;

    private List<Actor> npcs = new ArrayList<Actor>();

    public Interaction_Controller(Actor player, List<Actor> npcs) {
        this.player = player;
        this.npcs = npcs;
        bodyPJ = player.getBody();
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.X) {
            for (Actor npc :
                    npcs){
              //  (Math.round(bodyPJ.getWorldCenter().x) == Math.round((npc.getBody().getWorldCenter().x -0.5f))

               /* System.out.println( Math.round(bodyPJ.getWorldCenter().y));
                System.out.println(Math.round(npc.getBody().getWorldCenter().y));

                */
                if (isClose(npc)){
                    System.out.println("estoy teniendo un dialogo");
                    if (npc.refaceWithoutAnimation(Direction.getOpposite(player.getFacing()))) {

                        System.out.println("TE MIRO");
                        System.out.println("\n\n");
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean isClose (Actor npc){
        //esta abajo del npc
        if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f == Math.round((npc.getBody().getWorldCenter().y -0.8f)*10f)/10f
                && Math.round(bodyPJ.getWorldCenter().x) == Math.round(npc.getBody().getWorldCenter().x))
            return true;
        //esta arriba del npc
        if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f == Math.round((npc.getBody().getWorldCenter().y +0.8f)*10f)/10f
                && Math.round(bodyPJ.getWorldCenter().x) == Math.round(npc.getBody().getWorldCenter().x))
            return true;
        //esta a la derecha del npc
        if(Math.round(bodyPJ.getWorldCenter().x*10f)/10f == Math.round((npc.getBody().getWorldCenter().x +0.5f)*10f)/10f
                && Math.round(bodyPJ.getWorldCenter().y) == Math.round(npc.getBody().getWorldCenter().y))
            return true;
        //esta a la izquierda del npc
        if(Math.round(bodyPJ.getWorldCenter().x*10f)/10f == Math.round((npc.getBody().getWorldCenter().x -0.5f)*10f)/10f
                    && Math.round(bodyPJ.getWorldCenter().y) == Math.round(npc.getBody().getWorldCenter().y))
            return true;


            return  false;
    }



}

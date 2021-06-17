package Controller;

import Models.Direction;
import Models.ObjetosEstaticos.Door;
import Models.actor.Actor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.physics.box2d.Body;
import dialogue.Dialogue;

import java.util.ArrayList;
import java.util.List;

import static helper.Constante.PPM;


public class Interaction_Controller extends InputAdapter {

    private Actor player;

    private Body bodyPJ;

    private Door door;

    private DialogueController dialogueController;

    private List<Actor> npcs = new ArrayList<Actor>();


    public Interaction_Controller(Actor player, List<Actor> npcs,  Door door, DialogueController dialogueController) {
        this.player = player;
        this.npcs = npcs;
        bodyPJ = player.getBody();
        this.door=door;
        this.dialogueController = dialogueController;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.X) {
            for (Actor npc : npcs) {
                if (isClose(npc)) {
                    if (npc.refaceWithoutAnimation(Direction.getOpposite(player.getFacing()))) {
                        if(npc.getDialogue() != null){
                            Dialogue dialogue = npc.getDialogue();
                            dialogueController.startDialogue(dialogue);

                        }
                        break;
                    }
                }
            }
                if(isCloseDoor(door)){
                    player.getBody().setTransform(705/PPM, 310/PPM, 0);
                }
                return false;
            }

        return false;
    }

    public boolean isClose (Actor npc){
        //esta abajo del npc
       /* if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f >= Math.round((npc.getBody().getWorldCenter().y -0.8f)*10f)/10f
                && Math.round(bodyPJ.getWorldCenter().x) == Math.round(npc.getBody().getWorldCenter().x))
            return true;

        */
        //esta arriba del npc
        if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f <= Math.round((npc.getBody().getWorldCenter().y +0.8f)*10f)/10f
                && Math.round(bodyPJ.getWorldCenter().x) == Math.round(npc.getBody().getWorldCenter().x))
            if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f >= Math.round((npc.getBody().getWorldCenter().y -0.8f)*10f)/10f
                    && Math.round(bodyPJ.getWorldCenter().x) == Math.round(npc.getBody().getWorldCenter().x))
            return true;

        if(Math.round(bodyPJ.getWorldCenter().x*10f)/10f <= Math.round((npc.getBody().getWorldCenter().x +0.5f)*10f)/10f
                && Math.round(bodyPJ.getWorldCenter().y) == Math.round(npc.getBody().getWorldCenter().y))
            if(Math.round(bodyPJ.getWorldCenter().x*10f)/10f >= Math.round((npc.getBody().getWorldCenter().x -0.5f)*10f)/10f
                    && Math.round(bodyPJ.getWorldCenter().y) == Math.round(npc.getBody().getWorldCenter().y))
            return true;
        //esta a la izquierda del npc
        /*if(Math.round(bodyPJ.getWorldCenter().x*10f)/10f >= Math.round((npc.getBody().getWorldCenter().x -0.5f)*10f)/10f
                    && Math.round(bodyPJ.getWorldCenter().y) == Math.round(npc.getBody().getWorldCenter().y))
            return true;

         */


            return  false;
    }

    public boolean isCloseDoor (Door door){
        if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f == Math.round((door.getBody().getWorldCenter().y +0.8f)*10f)/10f && Math.round(bodyPJ.getWorldCenter().x) == Math.round(door.getBody().getWorldCenter().x)) {
            return true;
        }
        if (Math.round(bodyPJ.getWorldCenter().y * 10f) / 10f == Math.round((door.getBody().getWorldCenter().y - 0.7f) * 10f) / 10f && Math.round(bodyPJ.getWorldCenter().x) == Math.round(door.getBody().getWorldCenter().x)){
            return true;
        }
        return false;
    }



}

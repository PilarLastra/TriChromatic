package Controller;

import Models.Direction;
import Models.ObjetosEstaticos.Door;
import Models.actor.Actor;
import Screens.GameScreen;
import Screens.HouseScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.physics.box2d.Body;
import dialogue.Dialogue;
import Models.ObjetosEstaticos.Door;
import com.mygdx.game.MyGame;


import java.util.ArrayList;
import java.util.List;

import static helper.Constante.PPM;


public class Interaction_Controller extends InputAdapter {

    private Actor player;

    private Body bodyPJ;

    private Door door;
  
    private MyGame app;

    private List<Actor> npcs = new ArrayList<Actor>();


    private DialogueController dialogueController;

    private List<Actor> npcs = new ArrayList<Actor>();


    public Interaction_Controller(Actor player, List<Actor> npcs,  Door door, DialogueController dialogueController) {
        this.player = player;
        this.npcs = npcs;
        bodyPJ = player.getBody();
        this.door=door;
        this.dialogueController = dialogueController;

    public Interaction_Controller(Actor player, List<Actor> npcs, Door door, MyGame app) {
        this.player = player;
        this.npcs = npcs;
        this.bodyPJ = player.getBody();
        this.door = door;
        this.app = app;
    }

    public Interaction_Controller(Actor player, Door door, MyGame app) {
        this.player = player;
        this.bodyPJ = player.getBody();
        this.door = door;
        this.app = app;

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
                if(app.getScreen()instanceof GameScreen) {
                    app.getGameScreen().fading();
                }else app.getHouseScreen().fading();

            }
            return false;
        }
        return false;
    }

    public boolean isCloseNpc (Actor npc){
        if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f <= Math.round((npc.getBody().getWorldCenter().y +0.8f)*10f)/10f && Math.round(bodyPJ.getWorldCenter().x) == Math.round(npc.getBody().getWorldCenter().x))
        if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f >= Math.round((npc.getBody().getWorldCenter().y -0.8f)*10f)/10f && Math.round(bodyPJ.getWorldCenter().x) == Math.round(npc.getBody().getWorldCenter().x))
        return true;
        if(Math.round(bodyPJ.getWorldCenter().x*10f)/10f <= Math.round((npc.getBody().getWorldCenter().x +0.5f)*10f)/10f && Math.round(bodyPJ.getWorldCenter().y) == Math.round(npc.getBody().getWorldCenter().y))
        if(Math.round(bodyPJ.getWorldCenter().x*10f)/10f >= Math.round((npc.getBody().getWorldCenter().x -0.5f)*10f)/10f && Math.round(bodyPJ.getWorldCenter().y) == Math.round(npc.getBody().getWorldCenter().y))
        return true;
            return  false;
    }

    public boolean isCloseDoor (Door door){
        if(Math.round(bodyPJ.getWorldCenter().y*10f)/10f == Math.round((door.getBody().getWorldCenter().y +0.8f)*10f)/10f && Math.round(bodyPJ.getWorldCenter().x) == Math.round(door.getBody().getWorldCenter().x)) {
            return true;
        }

        if (Math.round(bodyPJ.getWorldCenter().y * 10f) / 10f == Math.round((door.getBody().getWorldCenter().y - 0.8f) * 10f) / 10f && ((bodyPJ.getWorldCenter().x) >= (door.getBody().getWorldCenter().x-0.4f) && (bodyPJ.getWorldCenter().x) <= (door.getBody().getWorldCenter().x+0.4f))){

            return true;
        }
        return false;
    }

}

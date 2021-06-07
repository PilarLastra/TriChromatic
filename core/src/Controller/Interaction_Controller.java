package Controller;

import Models.Direction;
import Models.actor.Actor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.List;


public class Interaction_Controller extends InputAdapter {

    private Actor player;

    private Actor npc;

    public Interaction_Controller(Actor player, Actor npc) {
        this.player = player;
        this.npc = npc;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.X) {
            if (player.getY() == npc.getY() - 1 && player.getX() == npc.getX() || player.getY() == npc.getY() + 1 && player.getX() == npc.getX()
                    || player.getX() == npc.getX() - 1 && player.getY() == npc.getY() || player.getX() == npc.getX() + 1 && player.getY() == npc.getY()) { //Controla si el npc esta alrededor del pj
                if (npc.refaceWithoutAnimation(Direction.getOpposite(player.getFacing())))
                    System.out.println("TE MIRO");
            }
            return false;
        }
        return false;
    }


}

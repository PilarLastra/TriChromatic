package Controller;

import Models.Direction;
import Models.actor.Actor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;
import java.util.List;


public class Interaction_Controller extends InputAdapter {

    private Actor player;

    private List<Actor> npcs = new ArrayList<Actor>();

    public Interaction_Controller(Actor player, List<Actor> npcs) {
        this.player = player;
        this.npcs = npcs;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.X) {
            for (Actor npc :
                    npcs) {
                if (player.getY() == npc.getY() - 0.5f && player.getX() == npc.getX() || player.getY() == npc.getY() + 0.5f && player.getX() == npc.getX()
                        || player.getX() == npc.getX() - 0.5f && player.getY() == npc.getY() || player.getX() == npc.getX() + 0.5f && player.getY() == npc.getY()) { //Controla si el npc esta alrededor del pj
                    if (npc.refaceWithoutAnimation(Direction.getOpposite(player.getFacing()))) {
                        System.out.println("TE MIRO");
                    }
                }
            }
            return false;
        }
        return false;
    }



}

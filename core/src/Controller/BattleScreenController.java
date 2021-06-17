package Controller;

import UI.DialogueBox;
import UI.PowerSelectBox;
import battle.Battle;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import power.PowerSpecification;

public class BattleScreenController extends InputAdapter {

        public enum STATE{
            SELECT_ACTION,
            DEACTIVATED,
            ;
        }

        private STATE state = STATE.DEACTIVATED;

        private final Battle battle;


        private final DialogueBox dialogue;

        private final PowerSelectBox powerSelectBox;

    public BattleScreenController(Battle battle, DialogueBox dialogueBox, PowerSelectBox options) { // en el constructor tambien va la caja de dialogos, caja de poderes y caja de opciones
        this.battle = battle;
        this.dialogue = dialogueBox;
        this.powerSelectBox = options;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (this.state == STATE.DEACTIVATED) {

            return false;
        }
        if (powerSelectBox.isVisible()) {
            if (keycode == Input.Keys.X) {
                battle.progress(powerSelectBox.getSelection());
                dialogue.setVisible(true);
                endTurn();
            } else if (keycode == Input.Keys.UP) {
                powerSelectBox.moveUp();
                return true;
            } else if (keycode == Input.Keys.DOWN) {
                powerSelectBox.moveDown();
                return true;
            } else if (keycode == Input.Keys.LEFT) {
                powerSelectBox.moveLeft();
                return true;
            } else if (keycode == Input.Keys.RIGHT) {
                powerSelectBox.moveRight();
                return true;
            }
        }
        return false;
    }


    public void restartTurn() {
        this.state = STATE.SELECT_ACTION;
        dialogue.setVisible(true);
        for (int i = 0; i <= 3; i++) {
            String label = "------";
            PowerSpecification spec = battle.getPlayer().getPowerSpecification(i);
            if (spec != null) {
                label = spec.getName();
            }


            powerSelectBox.setLabel(i, label.toUpperCase());
        }
        powerSelectBox.setVisible(true);

    }

    private void endTurn() {
        powerSelectBox.setVisible(false);
        this.state = STATE.DEACTIVATED;
    }
}

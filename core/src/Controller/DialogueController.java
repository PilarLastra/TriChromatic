package Controller;

import UI.DialogueBox;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import dialogue.*;


public class DialogueController extends InputAdapter {

    private DialogueTraverser traverser;
    private DialogueBox dialogueBox;


    public DialogueController(DialogueBox box) {
        this.dialogueBox = box;

    }

    @Override
    public boolean keyDown(int keycode) {
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }



    @Override
    public boolean keyUp(int keycode) {


        if (traverser != null && keycode == Input.Keys.Z && dialogueBox.isFinished()) { // continue through tree
            if (traverser.getType() == DialogueNode.NODE_TYPE.END) {
                traverser = null;
                dialogueBox.setVisible(false);
            } else if (traverser.getType() == DialogueNode.NODE_TYPE.LINEAR) {
                progress(0);
            }
            return true;
        }
            if (dialogueBox.isVisible()) {
                return true;
            }


        return false;

    }



    public void startDialogue(Dialogue dialogue) {
        traverser = new DialogueTraverser(dialogue);
        dialogueBox.setVisible(true);
        dialogueBox.animateText(traverser.getText());

    }

    private void progress(int index) {

        DialogueNode nextNode = traverser.getNextNode(index);
        dialogueBox.animateText(nextNode.getText());


    }

    public boolean isDialogueShowing() {
        return dialogueBox.isVisible();
    }
}

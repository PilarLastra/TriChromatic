package battle;

import UI.DialogueBox;
import actorPeleador.ActorPeleador;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;

import dialogue.Dialogue;
import dialogue.DialogueNode;

import power.Power;

public class Battle {

    private ActorPeleador player;

    private ActorPeleador opponent;


    private Stage uiStage;

    private Table root;

    private DialogueBox dialogueBox;

    private DialogueNode node1;
    private DialogueNode node2;
    private DialogueNode node3;
    private DialogueNode node4;


    private ActorPeleador aUser = null;
   private ActorPeleador target = null;

    public enum STATE{
        READY_TO_PROGRESS,
        WIN,
        LOSE,
        ;
    }

    private STATE state;

    private MyGame app;


    public Battle(ActorPeleador player, ActorPeleador opponent, MyGame app) {//en el constructor van los batallantes
        this.state = STATE.READY_TO_PROGRESS;
        this.player = player;
        this.opponent = opponent;
        this.app  = app;
    }

    public void setStateReady() {
        this.state = STATE.READY_TO_PROGRESS;
    }

    public void beginBattle (){


    }

    public void progress (int input){
        if (state != STATE.READY_TO_PROGRESS)
            return;
        playTurn(player, input, false);
        if (state == STATE.READY_TO_PROGRESS){
            playTurn(opponent, MathUtils.random(0,3), true);

        }
    }

    private void playTurn (ActorPeleador user, int input, boolean isNPC){

        initUI();
        Dialogue dNPC1 = new Dialogue();
        Power power;
        if (!isNPC){
            aUser = player;
            target = opponent;
            power = aUser.getPowers(input);
             node1 = new DialogueNode( aUser.getName()+" ha usado " + power.getName() + "!",0);

        }
        else
        {
            aUser = opponent;
            target = player;
            power = aUser.getPowers(input);
             node2 = new DialogueNode( aUser.getName()+" ha usado " + power.getName() + "!",1);
            node1.makeLinear(node2.getId());
            dNPC1.addNode(node1);
            dNPC1.addNode(node2);
            app.getBattleScreen().getDialogueController().startDialogue(dNPC1);

        }


        if (attemptHit(power)) {
            power.usePower(this, aUser, target);
        }


        if (player.isFainted()){

            this.state = STATE.LOSE;
        }
        else if (opponent.isFainted ()){

            this.state = STATE.WIN;
        }

    }

    private boolean attemptHit (Power power){
        float random = MathUtils.random(1.0f);
        if (power.getAccuracy() >= random){
            return  true;
        }
        return  false;
    }

    private boolean criticalHit() {
        float probability = 1f/16f;
        if (probability >= MathUtils.random(1.0f)) {
            return true;
        } else {
            return false;
        }
    }

    public int calculateDamage(Power power, ActorPeleador user) {



        int level = user.getLevel();
        float base = power.getPower();

        float modifier = MathUtils.random(0.85f, 1.00f);
        boolean isCritical = criticalHit ();
        if (isCritical) {
            modifier = modifier * 2f;
        }
        int damage = (int) ((  (2f*level+10f)/20  * base  ) * modifier);

        return damage;
    }



    public ActorPeleador getPlayer() {
        return player;
    }

    public ActorPeleador getOpponent() {
        return opponent;
    }


    public STATE getState() {
        return state;
    }

    public void initUI(){

        uiStage= new Stage(new ScreenViewport());
        //Dialogue setUp
        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);
        dialogueBox = new DialogueBox(app.getSkin());
        dialogueBox.setVisible(false);
        root.add(dialogueBox).expand().align(Align.bottomRight).pad(8f);;

    }

}


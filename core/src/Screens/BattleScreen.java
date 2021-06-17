package Screens;

import Controller.BattleScreenController;
import Controller.DialogueController;
import UI.*;
import actorPeleador.ActorPeleador;
import battle.Battle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.Setting;
import power.Power;

public class BattleScreen extends AbstractScreen {


    private Stage uiStage;

    private BattleScreenController controller;
    private DialogueController dialogueController;
    private InputMultiplexer inputMultiplexer;

    private Battle battle;





    private SpriteBatch batch;
    /*ACA VAN LOS RENDERER*/


    private OrthographicCamera camera;
    private float widthScreen, heightScreen;

    private Texture map;

    private ActorPeleador pj;
    private ActorPeleador opponent;
    private Power [] powers = new Power[4];

    private Table dialogueRoot;
    private DialogueBox dialogueBox;

    private Table statusBoxRoot;
    private DetailedStatusBox playerStatus;
    private DetailedStatusBox opponentStatus;

    private Table moveSelectRoot;
    private PowerSelectBox moveSelectBox;

    private ScreenViewport screenViewport;

    public BattleScreen(MyGame app) {
        super(app);
        batch = new SpriteBatch();
        screenViewport = new ScreenViewport();


         map = new Texture("Ui/Battle screen.jpg");

         pj = new ActorPeleador("poo", 3,powers);

         pj = pj.generatePower(pj, getApp().getPowerDatabase());





        Texture opSprite = new Texture("res/graphics/stattusefect/white.png");

         opponent = new ActorPeleador("tito", 1, powers);

        opponent = opponent.generatePower(opponent, getApp().getPowerDatabase());


        camera = new OrthographicCamera();
        widthScreen = Gdx.graphics.getWidth();
        heightScreen = Gdx.graphics.getHeight();
        camera.setToOrtho(false,widthScreen,heightScreen);

        battle = new Battle(pj, opponent, app);


        initUi ();


        inputMultiplexer = new InputMultiplexer();
       controller = new BattleScreenController(battle, dialogueBox, moveSelectBox);
       dialogueController = new DialogueController(dialogueBox);
        inputMultiplexer.addProcessor(0,controller);
        inputMultiplexer.addProcessor(1,dialogueController);

        battle.beginBattle();
        controller.restartTurn();




    }

    @Override
    public void render(float delta) {


        screenViewport.apply();



        update(Gdx.graphics.getDeltaTime());

        batch.begin();
        /*mete los sprites de los pjs

         */
        batch.draw(map,1,1,widthScreen, heightScreen);

        batch.end();
        uiStage.draw ();


        if (battle.getState() == Battle.STATE.READY_TO_PROGRESS) {

            controller.restartTurn();
        }
        else if(battle.getState() == Battle.STATE.WIN) {
            getApp().setScreen(getApp().getGameScreen());

        }
        else
           // getApp().setScreen(getApp().getHouseScreen());



        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {


            Gdx.app.exit();

        }
    }
/*
    public Dialogue setDialogue(){



        return dialogo;

    }

 */

    @Override
    public void resize(int width, int height) {

        uiStage.getViewport().update(
                (int)(Gdx.graphics.getWidth()/Setting.SCALE),
                (int)(Gdx.graphics.getHeight()/Setting.SCALE),
                true);


        screenViewport.update(width, height);
    }



    @Override
    public void update(float delta) {


        ((DetailedStatusBox)playerStatus).setHpTextPJ((int)pj.getHp(),pj.getMaxHp());
        ((DetailedStatusBox)opponentStatus).setHpTextPJ((int)opponent.getHp(),opponent.getMaxHp());


        uiStage.act();

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void initUi (){
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().update((int)(Gdx.graphics.getWidth()/ Setting.SCALE),
                (int)(Gdx.graphics.getHeight()/Setting.SCALE),
                true);
        statusBoxRoot = new Table();
        statusBoxRoot.setFillParent(true);
        uiStage.addActor(statusBoxRoot);

        playerStatus = new DetailedStatusBox(getApp().getSkin());
        playerStatus.setText(battle.getPlayer().getName());



        opponentStatus = new DetailedStatusBox(getApp().getSkin());
        opponentStatus.setText(battle.getOpponent().getName());

        statusBoxRoot.add(playerStatus).expand().align(Align.topLeft);
        statusBoxRoot.add(opponentStatus).expand().align(Align.topRight);

        /* MOVE SELECTION BOX */
        moveSelectRoot = new Table();
        moveSelectRoot.setFillParent(true);
        uiStage.addActor(moveSelectRoot);

        moveSelectBox = new PowerSelectBox(getApp().getSkin(), pj);
        moveSelectBox.setVisible(false);

        moveSelectRoot.add(moveSelectBox).expand().align(Align.bottomLeft);

        /* OPTION BOX */
        dialogueRoot = new Table();
        dialogueRoot.setFillParent(true);
        uiStage.addActor(dialogueRoot);



        /* DIALOGUE BOX */
        dialogueBox = new DialogueBox(getApp().getSkin());
        dialogueBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(dialogueBox).expand().align(Align.bottom).space(8f);

        dialogueRoot.add(dialogTable).expand().align(Align.bottom);





    }


    public DialogueController getDialogueController() {
        return dialogueController;
    }

    public Battle getBattle() {
        return battle;
    }

}

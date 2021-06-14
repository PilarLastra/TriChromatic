package Screeen;


import Controller.ControllerActor;
import Controller.Interaction_Controller;
import Models.*;
import Models.actor.Actor;
import Models.actor.LimitedWalkingBehavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;
import helper.TiledMapHelper;

import java.util.*;
import java.util.List;

import static helper.Constante.PPM;

public class GameScreen extends AbstractScreen {


    //Controladores
    private InputMultiplexer multiplexer;
    private Interaction_Controller interactionController;
    private ControllerActor controller;


    ///Creacion mundo, camara, actores, etc.
    private World world;
    private Actor pj;
    private List<LimitedWalkingBehavior> behaviors  = new ArrayList<LimitedWalkingBehavior>();
    private List<Actor> npcs  = new ArrayList<Actor>();;


    ///Creacion del batch
    private SpriteBatch batch;



    ///Renders de mapa y camara
    private OrthographicCamera camera;
    private float widthScreen, heightScreen;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMapHelper tiledMapHelper;

    //PJ



    // Metodos //

    public GameScreen(MyGame app) {
        super(app);

        batch = new SpriteBatch();
/*carga las animaciones del personaje y npcs*/

        TextureAtlas atlasPj = app.getAssetManager().get("PJ/player.atlas", TextureAtlas.class);

        //Se divide por dos pq se estima que x tile va a dar dos pasos
        AnimationSet animationsPJ = new AnimationSet(
                new Animation<TextureRegion>(0.3f / 2f, atlasPj.findRegions("dawn_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasPj.findRegions("dawn_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasPj.findRegions("dawn_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
        new Animation<TextureRegion>(0.3f / 2f, atlasPj.findRegions("dawn_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
                atlasPj.findRegion("dawn_stand_north"),
                atlasPj.findRegion("dawn_stand_south"),
                atlasPj.findRegion("dawn_stand_east"),
                atlasPj.findRegion("dawn_stand_west"));

        TextureAtlas atlasNPC = app.getAssetManager().get("PJ/PixiPili.atlas",TextureAtlas.class);

       AnimationSet animationsNPC = new AnimationSet(
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC.findRegions("camina_norte"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC.findRegions("camina_frente"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC.findRegions("camina_oeste"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC.findRegions("camina_este"), Animation.PlayMode.LOOP_PINGPONG),
                atlasNPC.findRegion("stand_norte"),
                atlasNPC.findRegion("stand frente"),
                atlasNPC.findRegion("stand_oeste"),
                atlasNPC.findRegion("stand_este"));



        camera = new OrthographicCamera();
        widthScreen = Gdx.graphics.getWidth();
        heightScreen = Gdx.graphics.getHeight();
        camera.setToOrtho(false,widthScreen,heightScreen);
        this.world = new World(new Vector2(0,0),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap();



        /*creacion de player*/
        pj = new Actor(20/PPM,70/PPM, animationsPJ,this,false);

        controller = new ControllerActor(pj);
        ///ver init UI

        ///  npc

        Random rnd = new Random();
        Actor npc = new Actor(500/PPM,150/PPM, animationsNPC,this,true);
        Actor npc2 = new Actor (400/PPM,110/PPM, animationsNPC,this,true);

         ///le asigna comportamiento al npc

         LimitedWalkingBehavior behavior1 = new LimitedWalkingBehavior(npc, 0.5f,0.5f,0,0,0,2,rnd);
         LimitedWalkingBehavior behavior2 = new LimitedWalkingBehavior(npc2, 0,0,1,1,0,2,rnd);

         addNpc(npc);
         addNpc(npc2);
         addBehavior(behavior1);
         addBehavior(behavior2);

         multiplexer = new InputMultiplexer();
        interactionController = new Interaction_Controller(pj, npcs);

        multiplexer.addProcessor(0, controller);
        multiplexer.addProcessor(1, interactionController);


       // box2DDebugRenderer.setDrawBodies(false); // Esta linea sirve para esconder las lines de los hit boxes

        }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void update(float delta) {
      /*  while (currentEvent == null || currentEvent.isFinished()) { // no active event
            if (eventQueue.peek() == null) { // no event queued up
                currentEvent = null;
                break;
            } else {					// event queued up
                currentEvent = eventQueue.poll();
                currentEvent.begin(this);
            }
        }

        if (currentEvent != null) {
            currentEvent.update(delta);
        }

        if (currentEvent == null) {

       */
        world.step(1/60f,6,2);
        controller.inputUpdateD(delta);
       // }

        //dialogueController.update(delta);

        //if (!dialogueBox.isVisible()) {
        cameraUpdate();
        orthogonalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);




        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit(); //Si apretas escape se cierra
        }
       /* }
        uiStage.act(delta);

        */
    }

    @Override
    public void render(float delta) {


        controller.inputUpdateD(delta);

        pj.update(delta);

        for (Actor actor:
                npcs) {
            actor.update(delta);
            behaviors.get(npcs.indexOf(actor)).update(delta);

        }


        update(Gdx.graphics.getDeltaTime());

      /*  if(Gdx.input.isKeyPressed(Input.Keys.F9)){
            MyGame.INSTANCE.newScreen();
        }

       */
        if(Gdx.input.isKeyPressed(Input.Keys.F8)) {
           getApp().screenPrincipa();
        }



      /*  Gdx.gl.glClearColor(0,0,0,1); //Limpia la pantalla color seteado = Negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       */

        orthogonalTiledMapRenderer.render();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));

        batch.begin(); //renderiza objetos

        batch.draw(pj.getSprite(),  pj.getBody().getPosition().x*PPM-10, pj.getBody().getPosition().y*PPM-10,
                17, 24);


        for (Actor npc:
                npcs) {batch.draw(npc.getSprite(), npc.getBody().getPosition().x*PPM-10, npc.getBody().getPosition().y*PPM-10,
                17, 24);
        }



        batch.end();
    }


        /*batch.begin();
        worldRenderer.render(batch, camera);
        queueRenderer.render(batch, currentEvent);
        if (renderTileInfo) {
            tileInfoRenderer.render(batch, Gdx.input.getX(), Gdx.input.getY());
        }
        batch.end();

        uiStage.draw();


    }

         */
// usa para las transiciones
    /*
    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
       /* uiStage.getViewport().update(width/uiScale, height/uiScale, true);
        gameViewport.update(width, height);


    }

     */

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
       /* if (currentEvent != null) {
            currentEvent.screenShow();
        }

        */
    }
/*
    private void initUI() {
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().update(Gdx.graphics.getWidth()/uiScale, Gdx.graphics.getHeight()/uiScale, true);
        //uiStage.setDebugAll(true);		// Uncomment to debug UI


        dialogRoot = new Table();
        dialogRoot.setFillParent(true);
        uiStage.addActor(dialogRoot);

        dialogueBox = new DialogueBox(getApp().getSkin());
        dialogueBox.setVisible(false);

        optionsBox = new OptionBox(getApp().getSkin());
        optionsBox.setVisible(false);

        Table dialogTable = new Table();
        dialogTable.add(optionsBox)
                .expand()
                .align(Align.right)
                .space(8f)
                .row();
        dialogTable.add(dialogueBox)
                .expand()
                .align(Align.bottom)
                .space(8f)
                .row();


        dialogRoot.add(dialogTable).expand().align(Align.bottom);


        menuRoot = new Table();
        menuRoot.setFillParent(true);
        uiStage.addActor(menuRoot);

        debugBox = new OptionBox(getApp().getSkin());
        debugBox.setVisible(false);

        Table menuTable = new Table();
        menuTable.add(debugBox).expand().align(Align.top | Align.left);

        menuRoot.add(menuTable).expand().fill();
    }

    public void changeWorld(World newWorld, int x, int y, DIRECTION face) {
        player.changeWorld(newWorld, x, y);
        this.world = newWorld;
        player.refaceWithoutAnimation(face);
        this.worldRenderer.setWorld(newWorld);
        this.camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
    }

    @Override
    public void changeLocation(World newWorld, int x, int y, DIRECTION facing, Color color) {
        getApp().startTransition(
                this,
                this,
                new FadeOutTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
                new FadeInTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
                new Action() {
                    @Override
                    public void action() {
                        changeWorld(newWorld, x, y, facing);
                    }
                });
    }
    */
/*
    @Override
    public World getWorld(String worldName) {
        return world.get(worldName);
    }

 */

    /*@Override
    public void queueEvent(CutsceneEvent event) {
        eventQueue.add(event);
    }

     */


    public World getWorld() {
        return world;
    }





    //Para que la camara siga al jugador
    private void cameraUpdate(){

        camera.position.set(new Vector3(pj.getBody().getPosition().x*PPM,pj.getBody().getPosition().y*PPM,0)); //Para libgdx el 0,0,0 del objeto camera esta abajo a la izquierda
        camera.update();
    }

/*


    @Override
    public void render(float delta) {

       // controller.inputUpdateW(delta);
        controller.inputUpdateD(delta);

        pj.update(delta);

        for (Actor actor:
                npcs) {
            actor.update(delta);
            behaviors.get(npcs.indexOf(actor)).update(delta);

        }


        update(Gdx.graphics.getDeltaTime());

        if(Gdx.input.isKeyPressed(Input.Keys.F9)){
            MyGame.INSTANCE.newScreen();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F8)) {
            MyGame.INSTANCE.screenPrincipa();
        }



        Gdx.gl.glClearColor(0,0,0,1); //Limpia la pantalla color seteado = Negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));

        batch.begin(); //renderiza objetos

        batch.draw(pj.getSprite(),  pj.getBody().getPosition().x*PPM-10, pj.getBody().getPosition().y*PPM-10,
                17, 24);


        for (Actor npc:
             npcs) {batch.draw(npc.getSprite(), npc.getBody().getPosition().x*PPM-10, npc.getBody().getPosition().y*PPM-10,
                17, 24);
        }



        batch.end();
    }




    @Override
    public void show() {

        Gdx.input.setInputProcessor(multiplexer);

    }

 */
public void addBehavior(LimitedWalkingBehavior a) {


    behaviors.add(a);
}

    public void addNpc(Actor a) {
        npcs.add(a);

    }




}


package Screens;


import Controller.ControllerActor;
import Controller.Interaction_Controller;
import Models.*;
import Models.ObjetosEstaticos.Door;
import Models.actor.Actor;
import Models.actor.LimitedWalkingBehavior;

import Screens.transition.FadeInTransition;
import Screens.transition.FadeOutTransition;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mygdx.game.MyGame;
import helper.TiledMapHelper;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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


    private Direction facing;

    ///Archivo
    private Gson gson;
    private FileWriter savefile;
    private JsonReader reader;

    ///Renders de mapa y camara
    private OrthographicCamera camera;
    private float widthScreen, heightScreen;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMapHelper tiledMapHelper;


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
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap("maps/Mapa1.tmx");



        /*creacion de player*/
        pj = new Actor(550/PPM,100/PPM, animationsPJ,this,false);

        controller = new ControllerActor(pj);


        /// puerta

        Door door = new Door(577/PPM, 147/PPM, this);

        ///  npc

        Random rnd = new Random();
        Actor npc = new Actor(500/PPM,150/PPM, animationsNPC,this,true);
        Actor npc2 = new Actor (400/PPM,110/PPM, animationsNPC,this,true);

        ///le asigna comportamiento al npc

        LimitedWalkingBehavior behavior1 = new LimitedWalkingBehavior(npc, 0.5f,0.5f,0.5f,0,0,2,rnd);
        LimitedWalkingBehavior behavior2 = new LimitedWalkingBehavior(npc2, 0.5f,0.5f,1,1,0,3,rnd);

        addNpc(npc);
        addNpc(npc2);
        addBehavior(behavior1);
        addBehavior(behavior2);

        multiplexer = new InputMultiplexer();

        interactionController = new Interaction_Controller(pj, npcs, door);

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
    public void update(float delta){


        world.step(1/60f,6,2);
        cameraUpdate();


        orthogonalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);


        if(Gdx.input.isKeyPressed(Input.Keys.F8)){
            gson = new Gson();
            try {
                reader = new JsonReader(new FileReader("saveFile.json"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Vector2 vectorsito = gson.fromJson(reader, Vector2.class);
            pj.getBody().setTransform(vectorsito, 0);
        }


        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            try {
                savefile = new FileWriter("saveFile.json");
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            gson = new Gson();

            gson.toJson(pj.getBody().getPosition(), savefile);
            try {
                savefile.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                savefile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gdx.app.exit(); //Si apretas escape se cierra
        }

    }

    @Override
    public void render(float delta) {


        controller.inputUpdateD(delta);

        pj.update(delta);

        for (Actor actor: npcs) {
            actor.update(delta);
            behaviors.get(npcs.indexOf(actor)).update(delta);
        }

        update(Gdx.graphics.getDeltaTime());

        if(Gdx.input.isKeyJustPressed(Input.Keys.F9)) {
            changeLocation(0,0, facing);
        }

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
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void changeLocation(int x, int y, Direction facing) {
        getApp().startTransition(
                this,
                this,
                new FadeOutTransition(2,  getApp().getTweenManager(), getApp().getAssetManager()),
                new FadeInTransition(2,  getApp().getTweenManager(), getApp().getAssetManager()));
    }

    public World getWorld() {
        return world;
    }

    //Para que la camara siga al jugador
    private void cameraUpdate(){

        camera.position.set(new Vector3(pj.getBody().getPosition().x*PPM,pj.getBody().getPosition().y*PPM,0)); //Para libgdx el 0,0,0 del objeto camera esta abajo a la izquierda
        camera.update();
    }

    public void addBehavior(LimitedWalkingBehavior a) {
        behaviors.add(a);
    }

    public void addNpc(Actor a) {
        npcs.add(a);
    }




}




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
    private FileWriter savefile1;
    private FileWriter savefile2;
    private JsonReader reader1;
    private JsonReader reader2;
    private String booleano;

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

        TextureAtlas atlasNPC2 = app.getAssetManager().get("PJ/pixiTito.atlas",TextureAtlas.class);

        AnimationSet animationsNPC2 = new AnimationSet(
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC2.findRegions("camina_norte"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC2.findRegions("camina_frente"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC2.findRegions("camina_oeste"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC2.findRegions("camina_este"), Animation.PlayMode.LOOP_PINGPONG),
                atlasNPC2.findRegion("stand_norte"),
                atlasNPC2.findRegion("stand frente"),
                atlasNPC2.findRegion("stand_oeste"),
                atlasNPC2.findRegion("stand_este"));

        TextureAtlas atlasNPC3 = app.getAssetManager().get("PJ/PixiDemi.atlas",TextureAtlas.class);

        AnimationSet animationsNPC3 = new AnimationSet(
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC3.findRegions("camina_norte"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC3.findRegions("camina_frente"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC3.findRegions("camina_oeste"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC3.findRegions("camina_este"), Animation.PlayMode.LOOP_PINGPONG),
                atlasNPC3.findRegion("stand_norte"),
                atlasNPC3.findRegion("stand frente"),
                atlasNPC3.findRegion("stand_oeste"),
                atlasNPC3.findRegion("stand_este"));

        TextureAtlas atlasNPC4 = app.getAssetManager().get("PJ/Enemigos.atlas",TextureAtlas.class);

        AnimationSet animationsNPC4 = new AnimationSet(
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC4.findRegions("camina_norte"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC4.findRegions("camina_frente"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC4.findRegions("camina_oeste"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlasNPC4.findRegions("camina_este"), Animation.PlayMode.LOOP_PINGPONG),
                atlasNPC4.findRegion("stand_norte"),
                atlasNPC4.findRegion("stand frente"),
                atlasNPC4.findRegion("stand_oeste"),
                atlasNPC4.findRegion("stand_este"));

        //-----------------------------------------------------------------------------------------------------------------------------------------------//

        camera = new OrthographicCamera();
        widthScreen = Gdx.graphics.getWidth();
        heightScreen = Gdx.graphics.getHeight();
        camera.setToOrtho(false,widthScreen,heightScreen);
        this.world = new World(new Vector2(0,0),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap("maps/Mapa1.tmx");

        /*creacion de player*/
        pj = new Actor(143/PPM,200/PPM, animationsPJ,this,false);

        controller = new ControllerActor(pj);


        /// puerta

        Door door = new Door(143/PPM, 210/PPM, this);

        ///  npc

        Random rnd = new Random();
        Actor npc = new Actor(500/PPM,150/PPM, animationsNPC,this,true);
        Actor npc2 = new Actor (400/PPM,110/PPM, animationsNPC2,this,true);
        Actor npc3 = new Actor (500/PPM,210/PPM, animationsNPC3,this,true);
        Actor npc4 = new Actor (150/PPM,600/PPM, animationsNPC4,this,true);

        ///le asigna comportamiento al npc

        LimitedWalkingBehavior behavior1 = new LimitedWalkingBehavior(npc, 0.5f,0.5f,0.5f,0,0,2,rnd);
        LimitedWalkingBehavior behavior2 = new LimitedWalkingBehavior(npc2, 0.5f,0.5f,1,1,0,3,rnd);
        LimitedWalkingBehavior behavior3 = new LimitedWalkingBehavior(npc3, 1,1,1,1,0,3,rnd);
        LimitedWalkingBehavior behavior4 = new LimitedWalkingBehavior(npc4, 0,0,1,1,0,1,rnd);

        addNpc(npc);
        addNpc(npc2);
        addNpc(npc3);
        addNpc(npc4);
        addBehavior(behavior1);
        addBehavior(behavior2);
        addBehavior(behavior3);
        addBehavior(behavior4);

        multiplexer = new InputMultiplexer();

        interactionController = new Interaction_Controller(pj, npcs, door, app);

        multiplexer.addProcessor(0, controller);
        multiplexer.addProcessor(1, interactionController);


        box2DDebugRenderer.setDrawBodies(false); // Esta linea sirve para esconder las lines de los hit boxes

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
                reader1 = new JsonReader(new FileReader("positionSaveFile.json"));
                reader2 = new JsonReader(new FileReader("screenSaveFile.json"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Vector2 vectorsito = gson.fromJson(reader1, Vector2.class);
            booleano = gson.fromJson(reader2, String.class);
            pj.getBody().setTransform(vectorsito, 0);
            if(booleano.equals("false")){
                getApp().setScreen(getApp().getHouseScreen());
            }
        }


        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gson = new Gson();
            booleano ="true"; // Estamos en gamescreen por lo tanto es true

            try {
                savefile1 = new FileWriter("positionSaveFile.json");
                savefile2 = new FileWriter("screenSaveFile.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

            gson.toJson(pj.getBody().getPosition(), savefile1);
            gson.toJson(booleano, savefile2);

            try {
                savefile1.flush();
                savefile2.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                savefile1.close();
                savefile2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }






            Gdx.app.exit(); //Si apretas escape se cierra
        }

    }

    @Override
    public void render(float delta) {


        controller.inputUpdateD(delta, 1);

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){  // CORRER
            controller.inputUpdateD(delta, 3);
        }

        pj.update(delta);

        for (Actor actor: npcs) {
            actor.update(delta);
            behaviors.get(npcs.indexOf(actor)).update(delta);
        }

        update(Gdx.graphics.getDeltaTime());

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

    public void fading() {
        getApp().startTransition(
                this,
                getApp().getHouseScreen(),
                new FadeOutTransition(1,  getApp().getTweenManager(), getApp().getAssetManager(), 1),
                new FadeInTransition(1,  getApp().getTweenManager(), getApp().getAssetManager(), 1));
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




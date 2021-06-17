package Screens;

import Controller.ControllerActor;
import Controller.Interaction_Controller;
import Models.AnimationSet;
import Models.ObjetosEstaticos.Door;
import Models.actor.Actor;
import Screens.transition.FadeInTransition;
import Screens.transition.FadeOutTransition;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
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

import static helper.Constante.PPM;

public class HouseScreen extends AbstractScreen{

    private Batch batch;
    private OrthographicCamera camera;
    private float widthScreen, heightScreen;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private Box2DDebugRenderer box2DDebugRenderer;
    private TiledMapHelper tiledMapHelper;

    // Controladores

    private Interaction_Controller interactionController;
    private ControllerActor controller;
    private InputMultiplexer multiplexer;

    // Mundo y PJ

    private World world;
    private Actor pj;

    ///Archivo
    private Gson gson;
    private FileWriter savefile1;
    private FileWriter savefile2;
    private JsonReader reader1;
    private JsonReader reader2;
    private String booleano;

    public HouseScreen(MyGame app) {
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


        camera = new OrthographicCamera();
        widthScreen = Gdx.graphics.getWidth();
        heightScreen = Gdx.graphics.getHeight();
        camera.setToOrtho(false,widthScreen,heightScreen);
        this.world = new World(new Vector2(0,0),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap("maps/Mapa2.tmx");

        // PJ

        pj = new Actor(505/PPM, 410/PPM, animationsPJ,this,false);

        controller = new ControllerActor(pj);

        // Puerta

        Door door = new Door(670/PPM, 246/PPM, this);

        // Interaction Controller

        interactionController = new Interaction_Controller(pj, door, app);

        // Multiplexer

        multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(0, controller);
        multiplexer.addProcessor(1, interactionController);

        // Ver o no las hitboxes

        box2DDebugRenderer.setDrawBodies(false);
    }




    @Override
    public void update(float delta) {

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
            if(booleano.equals("true")){
                getApp().setScreen(getApp().getGameScreen());
            }
        }


        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            gson = new Gson();
            booleano ="false"; // Estamos en gamescreen por lo tanto es true

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

        update(Gdx.graphics.getDeltaTime());

        orthogonalTiledMapRenderer.render();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));

        batch.begin(); //renderiza objetos

        batch.draw(pj.getSprite(),  pj.getBody().getPosition().x*PPM-10, pj.getBody().getPosition().y*PPM-10,
                17, 24);

        batch.end();
/*
        if(getApp().getBattleScreen().getBattle().getState() == Battle.STATE.LOSE)
        {
            Dialogue win = new Dialogue();
            DialogueNode node4 = new DialogueNode("Haz caido derrotado...",0);
            win.addNode(node4);
            dialogueController.startDialogue(lose);
            getApp().getBattleScreen().getBattle().setStateReady();

        }
*/        // ESTO IRA CUANDO HAYA IMPLEMENTACION DE DIALOGO


    }

    public World getWorld() {
        return world;
    }

    public void fading() {
        getApp().startTransition(
                this,
                getApp().getGameScreen(),
                new FadeOutTransition(1,  getApp().getTweenManager(), getApp().getAssetManager(), 1),
                new FadeInTransition(1,  getApp().getTweenManager(), getApp().getAssetManager(), 1));
    }

    private void cameraUpdate(){
        camera.position.set(new Vector3(pj.getBody().getPosition().x*PPM,pj.getBody().getPosition().y*PPM,0)); //Para libgdx el 0,0,0 del objeto camera esta abajo a la izquierda
        camera.update();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }
}

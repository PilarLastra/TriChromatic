package com.mygdx.game;


import Controller.ControllerActor;
import Controller.Interaction_Controller;
import Models.*;
import Models.actor.Actor;
import Models.actor.Actor_Behavior;
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
import helper.TiledMapHelper;

import java.util.*;

import static helper.Constante.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;

    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMapHelper tiledMapHelper;

    //PJ
    private Texture playerStandig;
    private ControllerActor controller;
    private Actor pj;

   // private Actor npc;

   // private LimitedWalkingBehavior npc1;

    private Interaction_Controller interactionController;


    private List<LimitedWalkingBehavior> behaviors  = new ArrayList<LimitedWalkingBehavior>();

    private List<Actor> npcs  = new ArrayList<Actor>();


    private InputMultiplexer multiplexer;


    // Metodos //

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;

        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        TextureAtlas atlas = MyGame.INSTANCE.getAssetManager().get("PJ/player.atlas", TextureAtlas.class);
        //Se divide por dos pq se estima que x tile va a dar dos pasos
        AnimationSet animations = new AnimationSet(
                new Animation<TextureRegion>(0.3f / 2f, atlas.findRegions("dawn_walk_north"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlas.findRegions("dawn_walk_south"), Animation.PlayMode.LOOP_PINGPONG),
                new Animation<TextureRegion>(0.3f / 2f, atlas.findRegions("dawn_walk_east"), Animation.PlayMode.LOOP_PINGPONG),
        new Animation<TextureRegion>(0.3f / 2f, atlas.findRegions("dawn_walk_west"), Animation.PlayMode.LOOP_PINGPONG),
                atlas.findRegion("dawn_stand_north"),
                atlas.findRegion("dawn_stand_south"),
                atlas.findRegion("dawn_stand_east"),
                atlas.findRegion("dawn_stand_west"));


        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap();



        //PJ
        playerStandig = new Texture ("PJ/pj0.png");

        pj = new Actor(1,1, animations);

        controller = new ControllerActor(pj);

        ///  npc

        Random rnd = new Random();

      //  npc = new Actor(8,5, animations);
        Actor npc = new Actor(8,5,animations);

        Actor npc2 = new Actor (8,1,animations);



         ///le asigna comportamiento al npc

         LimitedWalkingBehavior behavior1 = new LimitedWalkingBehavior(npc, 0.5f,0.5f,0,0,0,1,rnd);
         LimitedWalkingBehavior behavior2 = new LimitedWalkingBehavior(npc2, 0,0,0.5f,0.5f,0,1,rnd);

         addNpc(npc);
         addNpc(npc2);
         addBehavior(behavior1);
         addBehavior(behavior2);

         multiplexer = new InputMultiplexer();


        interactionController = new Interaction_Controller(pj, npcs);


        multiplexer.addProcessor(0, controller);
        multiplexer.addProcessor(1, interactionController);


        box2DDebugRenderer.setDrawBodies(false); // Esta linea sirve para esconder las lines de los hit boxes


        }

    public World getWorld() {
        return world;
    }



    private void update(float delta){

        float accel = 0;

        world.step(1/60f,6,2);
        cameraUpdate();


        orthogonalTiledMapRenderer.setView(camera);
        batch.setProjectionMatrix(camera.combined);



        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit(); //Si apretas escape se cierra
        }

    }

    //Para que la camara siga al jugador
    private void cameraUpdate(){
        camera.position.set(new Vector3(pj.getWorldX()* Setting.SCALED_TILE_SIZE,pj.getWorldY()* Setting.SCALED_TILE_SIZE,0)); //Para libgdx el 0,0,0 del objeto camera esta abajo a la izquierda
        camera.update();
    }

    public void addBehavior(LimitedWalkingBehavior a) {


        behaviors.add(a);
    }

    public void addNpc(Actor a) {
        npcs.add(a);

    }


    @Override
    public void render(float delta) {

        controller.update(delta);

        pj.update(delta);

        for (Actor actor:
                npcs) {
            actor.update(delta);
            behaviors.get(npcs.indexOf(actor)).update(delta);

        }
       /* npc.update(delta);
        npc1.update(delta);

        */



        update(Gdx.graphics.getDeltaTime());


        Gdx.gl.glClearColor(0,0,0,1); //Limpia la pantalla color seteado = Negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));

        batch.begin(); //renderiza objetos

        batch.draw(pj.getSprite(), pj.getWorldX() * Setting.SCALED_TILE_SIZE , pj.getWorldY() * Setting.SCALED_TILE_SIZE,
                Setting.SCALED_TILE_SIZE * 0.4f, Setting.SCALED_TILE_SIZE*0.5f);


        for (Actor npc:
             npcs) {batch.draw(npc.getSprite(), npc.getWorldX() * Setting.SCALED_TILE_SIZE , npc.getWorldY() * Setting.SCALED_TILE_SIZE,
                Setting.SCALED_TILE_SIZE * 0.4f, Setting.SCALED_TILE_SIZE*0.5f);
        }

        batch.end();
    }



    @Override
    public void show() {

        Gdx.input.setInputProcessor(multiplexer);

    }


}


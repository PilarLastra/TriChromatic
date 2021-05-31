package com.mygdx.game;

import Models.Controller;
import Models.Actor;
import Models.Setting;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TiledMapHelper;

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
    private Controller controller;
    private Actor pj;




    // Metodos //

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;

        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();


        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap();

        //PJ
        playerStandig = new Texture ("PJ/pj0.png");

        pj = new Actor(1,1);

        controller = new Controller(pj);

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



    @Override
    public void render(float delta) {

        pj.update(delta);
        update(Gdx.graphics.getDeltaTime());


        Gdx.gl.glClearColor(0,0,0,1); //Limpia la pantalla color seteado = Negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));



        batch.begin(); //renderiza objetos



        batch.draw(playerStandig, pj.getWorldX() * Setting.SCALED_TILE_SIZE , pj.getWorldY() * Setting.SCALED_TILE_SIZE,
                Setting.SCALED_TILE_SIZE * 0.4f,Setting.SCALED_TILE_SIZE*0.5f);


        batch.end();




    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(controller);



    }
}


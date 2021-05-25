package com.mygdx.game;

import Models.Player;
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

import java.awt.*;

import static helper.Constante.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TiledMapHelper tiledMapHelper;

    //PJ
    private Texture playerTexture;
    private Rectangle pjRectangle;
    private Player pj;








    // Metodos //

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0),false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tiledMapHelper = new TiledMapHelper(this);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap();

        //PJ
        playerTexture = new Texture(Gdx.files.internal("PJ/PJ.png"));


        //Crear un rectangulo para representar al personaje logicamente
        pjRectangle = new Rectangle();
        pjRectangle.x = 0;
        pjRectangle.y = 0;
        pjRectangle.width = 64;
        pjRectangle.height = 64;

        pj = new Player("Poo",3, pjRectangle, playerTexture);


        }

    public World getWorld() {
        return world;
    }

    private void update(float delta){

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
        camera.position.set(new Vector3(pj.getPj().x,pj.getPj().y,0)); //Para libgdx el 0,0,0 del objeto camera esta abajo a la derecha
        camera.update();




    }



    @Override
    public void render(float delta) {

        update(Gdx.graphics.getDeltaTime());


        Gdx.gl.glClearColor(0,0,0,1); //Limpia la pantalla color seteado = Negro
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        box2DDebugRenderer.render(world,camera.combined.scl(PPM));



        batch.begin(); //renderiza objetos

        batch.draw(playerTexture,pj.getPj().x,pj.getPj().y);


        batch.end();

        //Movimiento del pj
        pj.playerMove();
        //Hay que verificando si el PJ esta en la pantalla


    }
/*
    public void playerMove (){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            pj.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            pj.x += 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            pj.y += 200 * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            pj.y -= 200 * Gdx.graphics.getDeltaTime();
        }
    }

*/




}


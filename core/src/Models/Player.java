package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.GameScreen;

import java.awt.*;
import java.awt.geom.RectangularShape;

public class Player {


    private String nombre;
    private int vida;
    private int danio;
    private Texture player;
    private Rectangle pj;
    private BodyDef playerbody;
    private GameScreen gameScreen;





    public Player(String nombre, int danio, GameScreen gameScreen) {
        this.nombre = nombre;
        this.vida = 100;
        this.danio = danio;
        this.player = new Texture(Gdx.files.internal("PJ/PJ.png"));
        this.pj = new Rectangle();
        this.playerbody = new BodyDef();
        this.gameScreen = gameScreen;
    }

    public Rectangle getPj() {
        return pj;
    }

    public Texture getPlayer() {
        return player;
    }


    //Crear un rectangulo para representar al personaje logicamente
    public void PlayerRectangle(){

        pj.x =0;
        pj.y = 0;
        pj.width = 64;
        pj.height = 64;

    }

    //Movimiento del PJ (el 200 * ... logra que el movimiento se traduzca a pixeles asi el pj se mueve fluido y no va pegando saltos de tile en tile)
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

    //Le voy a tratar dar gravedad
    public void PjBodyGravity(){
        playerbody.type = BodyDef.BodyType.DynamicBody; //Un objeto dinamico se mueve y es afectado x los objetos estaticos, etc
        playerbody.position.set(pj.x,pj.y);
        Body body = gameScreen.getWorld().createBody(playerbody);





    }
















}

package Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameScreen;
import helper.TiledMapHelper;

import java.awt.*;
import java.awt.geom.RectangularShape;

import static helper.Constante.PPM;

//Le cambie el nombre de player a actor pq puede haber mas actores en pantalla
public class Actor {

    private TiledMap map;
    private int x;
    private int y;

    private float worldX, worldY;

    private int srcX, srcY;
    private int destX, destY; //destino
    private float animTimer;
    private float ANIM_TIMER = 0.5f; //Tiempo que dura la animacion

    private ACTOR_STATE state; //Para saber el estado actual del actor



// Metodos //
    public Actor(int x, int y) {
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
        this.state = ACTOR_STATE.STANDING;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getWorldX() {
        return worldX;
    }

    public float getWorldY() {
        return worldY;
    }

    //Estados en los que estara x ahora nuestro actor
    public enum ACTOR_STATE{
        WALKING,
        STANDING,
        ;
    }
//Delta es el tiempo del ultimo fram (creeo)
    public void update(float delta){
        if(state == ACTOR_STATE.WALKING){
            animTimer += delta;
            worldX = Interpolation.linear.apply(srcX,destX,animTimer/ANIM_TIMER);
            worldY = Interpolation.linear.apply(srcY,destY,animTimer/ANIM_TIMER);
            if(animTimer > ANIM_TIMER){
                finishMove();
            }
        }

    }

    public boolean move (int dx, int dy){

        //Hay que chekear si nos estamos moviendo pq si nos estamos moviendo no tendriamos q poder hacer otro movimiento
        if(state != ACTOR_STATE.STANDING){
            return  false;
        }

        // No se va de los limites del mapa sa sa saaaaaaaaaaaaaaaaaa
        if( x+dx <0 || x + dx >=Setting.MAP_WIDE || y + dy <0 || y + dy >= Setting.MAP_HEIGHT){
            return false;
        }
        initializeMove(x , y  ,dx , dy);

        x += dx;
        y += dy;

        return true;

    }
//Necesita saber a donde vamos y de donde venimos (full espiritual el metodo)
    private void initializeMove(int oldX, int oldY, int dx, int dy){
        this.srcX = oldX;
        this.srcY = oldY;
        this.destX= oldX + dx;
        this.destY = oldY + dy;
        this.worldX = oldX;
        this.worldY = oldY;
        animTimer = 0f;
        state = ACTOR_STATE.WALKING;


    }
    private void finishMove(){
        state = ACTOR_STATE.STANDING;
        this.worldX = destX;
        this.worldY = destY;
        this.srcX = 0;
        this.srcY = 0;
        this.destX = 0;
        this.destY = 0;

    }




    /*
    public Body crearPlayer() {

        playerStatus = new PlayerStatus(1, 3);

        BodyDef playerbody = new BodyDef();
        playerbody.position.x = playerStatus.position.x;
        playerbody.position.y = playerStatus.position.y;
        playerbody.type = BodyDef.BodyType.DynamicBody; //Un objeto dinamico se mueve y es afectado x los objetos estaticos, etc

        Body body = gameScreen.getWorld().createBody(playerbody);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(playerStatus.WIDTH, playerStatus.HEIGHT);

        body.createFixture(shape, 1000);
        body.setUserData(playerStatus);

        shape.dispose();

        return body;

    }
    */

}
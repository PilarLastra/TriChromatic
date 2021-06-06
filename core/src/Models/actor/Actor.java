package Models.actor;

import Models.AnimationSet;
import Models.Direction;
import com.mygdx.game.Setting;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Interpolation;

//Le cambie el nombre de player a actor pq puede haber mas actores en pantalla
public class Actor {

    private TiledMap map;
    private int x;
    private int y;
    private Direction facing;

    private float worldX, worldY;

    private int srcX, srcY;
    private int destX, destY; //destino
    private float animTimer;
    private float ANIM_TIMER = 0.3f; //Tiempo que dura la animacion

    private float walkTimer; //Se nececita sabes cuanto tiempo lleva el actor caminando ya que si da un "paso" la animacion sera diferente a si camina dos tiles enteros
    private boolean moveRequestThisFrame;


    private ACTOR_STATE state; //Para saber el estado actual del actor

    private AnimationSet animations;



// Metodos //

    public Actor(int x, int y, AnimationSet animations) {
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
        this.animations = animations;
        this.state = ACTOR_STATE.STANDING;
        this.facing = Direction.SOUTH; //Despues se sobrescribe
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

    public TextureRegion getSprite(){
        if (state == ACTOR_STATE.WALKING){
            return animations.getWalking(facing).getKeyFrame(walkTimer);
        }else if (state == ACTOR_STATE.STANDING){
            return animations.getStanding(facing);
        }
        return animations.getStanding(Direction.SOUTH); //Esto no se va a ver nunca pero x las dudas
    }

    //Estados en los que estara x ahora nuestro actor
    public enum ACTOR_STATE{
        WALKING,
        STANDING,
        ;
    }

//Delta es el tiempo del ultimo frame (creo)
    public void update(float delta){
        if(state == ACTOR_STATE.WALKING){
            animTimer += delta;
            walkTimer += delta;
            worldX = Interpolation.linear.apply(srcX,destX,animTimer/ANIM_TIMER);
            worldY = Interpolation.linear.apply(srcY,destY,animTimer/ANIM_TIMER);

            if(animTimer > ANIM_TIMER){//Si la animacion termino
                float leftOverTime = animTimer - ANIM_TIMER;
                walkTimer -= leftOverTime;
                finishMove();
                if(moveRequestThisFrame){
                    move(facing);
                }else{
                    walkTimer = 0f;
                }
            }
        }
        moveRequestThisFrame = false;

    }

    public boolean move (Direction dir){

        //Hay que chekear si nos estamos moviendo pq si nos estamos moviendo no tendriamos q poder hacer otro movimiento
        if(state == ACTOR_STATE.WALKING){
            if(facing == dir){//Si estamos mirando al mismo lugar que queremos caminar
                moveRequestThisFrame = true;
            }
            return  false;
        }

        // No se va de los limites del mapa sa sa saaaaaaaaaaaaaaaaaa
        if( x+ dir.getDx() <0 || x + dir.getDx() >= Setting.MAP_WIDE || y + dir.getDy() <0 || y + dir.getDy() >= Setting.MAP_HEIGHT){
            return false;
        }
        initializeMove(dir);

        x += dir.getDx();
        y += dir.getDy();

        return true;

    }

//Necesita saber a donde vamos y de donde venimos (full espiritual el metodo)
    private void initializeMove(Direction dir){
        this.facing = dir;
        this.srcX = x;
        this.srcY = y;
        this.destX= x + dir.getDx();
        this.destY = y + dir.getDy();
        this.worldX = x;
        this.worldY = y;
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



    public ACTOR_STATE getMovementState() {
        return state;
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
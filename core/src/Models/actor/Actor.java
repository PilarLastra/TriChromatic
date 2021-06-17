package Models.actor;

import Models.AnimationSet;
import Models.Direction;
import battle.Battle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import Screeen.GameScreen;
import com.mygdx.game.Setting;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

import dialogue.Dialogue;

import static helper.Constante.PPM;

//Le cambie el nombre de player a actor pq puede haber mas actores en pantalla
public class Actor {

    private float x;
    private float y;
    private Direction facing;


    private float destX, destY; //destino
    private float animTimer;
    private float ANIM_TIMER = 0.1f; //Tiempo que dura la animacion

    private float walkTimer; //Se nececita sabes cuanto tiempo lleva el actor caminando ya que si da un "paso" la animacion sera diferente a si camina dos tiles enteros
    private boolean moveRequestThisFrame;

    private Dialogue dialogue;

    private int index;


    private Texture dialSprite;

    private Body player;

    private ACTOR_STATE state; //Para saber el estado actual del actor

    private AnimationSet animations;

    private GameScreen gameScreen;




// Metodos //

    public Actor(float x, float y, Texture dialSprite, AnimationSet animations, GameScreen gameScreen, boolean isNPC, int index) {
        this.x = x;
        this.y = y;
        this.animations = animations;
        this.state = ACTOR_STATE.STANDING;
        this.dialSprite = dialSprite;
        this.gameScreen = gameScreen;
        this.player = crearPlayer(isNPC);
        this.facing = Direction.SOUTH; //Despues se sobrescribe
        this.index= index;
    }


    public int getIndex() {
        return index;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
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

    public void setStateStanding() {
        this.state = ACTOR_STATE.STANDING;
    }

    //Delta es el tiempo del ultimo frame (creo)
    public void update(float delta){
        if(state == ACTOR_STATE.WALKING){
            animTimer += delta;
            walkTimer += delta;
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
        if(player.getPosition().x <0 || player.getPosition().x + dir.getDx() >= Setting.MAP_WIDE || player.getPosition().y + dir.getDy() <0 || player.getPosition().y + dir.getDy() >= Setting.MAP_HEIGHT){

            return false;
        }
        initializeMove(dir);

        x += dir.getDx();
        y += dir.getDy();



        return true;

    }

    public Texture getDialSprite() {
        return dialSprite;
    }

    //Necesita saber a donde vamos y de donde venimos (full espiritual el metodo)
    private void initializeMove(Direction dir){
        this.facing = dir;
        this.destX= x + dir.getDx();
        this.destY = y + dir.getDy();
        animTimer = 0f;
        state = ACTOR_STATE.WALKING;

    }

    private void finishMove(){
        state = ACTOR_STATE.STANDING;
        this.destX = 0;
        this.destY = 0;

    }


    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }


    public boolean refaceWithoutAnimation(Direction dir) {
        if (state != ACTOR_STATE.STANDING) { // can only reface when standing
            return false;
        }
        this.facing = dir;
        return true;
    }

    public Direction getFacing() {
        return facing;
    }



    public ACTOR_STATE getMovementState() {
        return state;
    }


    public Body getBody() {
        return player;
    }

    public Body crearPlayer(boolean isNPC) {

        BodyDef playerbody = new BodyDef();
        playerbody.position.x = getX();
        playerbody.position.y = getY();
        if (isNPC)
            playerbody.type = BodyDef.BodyType.KinematicBody; //Un objeto kinematico se mueve pero no es afectado por otros objetos
        else {
            playerbody.type = BodyDef.BodyType.DynamicBody; //Un objeto dinamico se mueve y es afectado x los objetos estaticos, etc
        }
        playerbody.fixedRotation = true;
        player = gameScreen.getWorld().createBody(playerbody);
        PolygonShape shape = new PolygonShape();
        if (isNPC)
            shape.setAsBox(17/4/PPM, 24/4/PPM);
        else
            shape.setAsBox(17/2/PPM, 24/2/PPM);
        player.createFixture(shape, 1.0F);
        shape.dispose();

        return player;

    }


}
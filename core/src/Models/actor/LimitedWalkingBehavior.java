package Models.actor;

import Models.Direction;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Random;

//clase que indica el movimiento de los NPC

public class LimitedWalkingBehavior extends Actor_Behavior{

    private float moveIntervalMinimum;
    private float moveIntervalMaximum;
    private Random random;
    private Body npcBody;
    private float timer;
    private float currentWaitTime;

    private float dx, dy;
    private float limNorth, limSouth, limEast, limWest;

    public LimitedWalkingBehavior(Actor actor, float limNorth, float limSouth, float limEast, float limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random) {
        super(actor);
        this.moveIntervalMinimum = moveIntervalMinimum;
        this.moveIntervalMaximum = moveIntervalMaximum;
        this.random = random;
        this.timer = 0f;
        this.currentWaitTime = calculateWaitTime();
        this.npcBody = actor.getBody();
        this.limNorth = limNorth+npcBody.getPosition().y;
        this.limSouth = limSouth-npcBody.getPosition().y;
        this.limEast = limEast+npcBody.getPosition().x;
        this.limWest = limWest-npcBody.getPosition().x;


    }

    @Override
    public void update(float delta) {
        if (getActor().getMovementState() != Actor.ACTOR_STATE.STANDING) {
            return;
        }
        timer += delta;
        npcBody.setLinearVelocity(0,0);
        if (timer >= currentWaitTime) {
            int directionIndex = random.nextInt(Direction.values().length);
            Direction moveDirection = Direction.values()[directionIndex];

            if (npcBody.getPosition().x+moveDirection.getDx() > this.limEast || -(npcBody.getPosition().x+moveDirection.getDx()) > this.limWest || npcBody.getPosition().y+moveDirection.getDy() > this.limNorth || -(npcBody.getPosition().y+moveDirection.getDy()) > this.limSouth) {
                currentWaitTime = calculateWaitTime();
                timer = 0f;
                return;
            }
            boolean moved = getActor().move(moveDirection);

            if (moved) {
                moveNpc(moveDirection);

                //this.dy += moveDirection.getDy();


            }

            currentWaitTime = calculateWaitTime();
            timer = 0f;
        }
    }

    public void moveNpc (Direction direction){
        int horizontalForce =0;

        if(direction == Direction.EAST){
            horizontalForce +=1;
            getActor().move(Direction.EAST);
        }
        if(direction == Direction.WEST){
            getActor().move(Direction.WEST);
            horizontalForce -=1;
        }
        npcBody.setLinearVelocity(horizontalForce * 2, npcBody.getLinearVelocity().y);
    }

    private float calculateWaitTime() {
        return random.nextFloat() * (moveIntervalMaximum - moveIntervalMinimum) + moveIntervalMinimum;
    }
}


package Models.actor;

import Models.Direction;
import com.badlogic.gdx.math.GridPoint2;

import java.util.Random;

//clase que indica el movimiento de los NPC

public class LimitedWalkingBehavior extends Actor_Behavior{

    private float moveIntervalMinimum;
    private float moveIntervalMaximum;
    private Random random;

    private float timer;
    private float currentWaitTime;

    private GridPoint2 moveDelta;
    private int limNorth, limSouth, limEast, limWest;

    public LimitedWalkingBehavior(Actor actor, int limNorth, int limSouth, int limEast, int limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random) {
        super(actor);
        this.limNorth = limNorth;
        this.limSouth = limSouth;
        this.limEast = limEast;
        this.limWest = limWest;
        this.moveIntervalMinimum = moveIntervalMinimum;
        this.moveIntervalMaximum = moveIntervalMaximum;
        this.random = random;
        this.timer = 0f;
        this.currentWaitTime = calculateWaitTime();
        this.moveDelta = new GridPoint2();
    }

    @Override
    public void update(float delta) {
        if (getActor().getMovementState() != Actor.ACTOR_STATE.STANDING) {
            return;
        }

        timer += delta;
        if (timer >= currentWaitTime) {
            int directionIndex = random.nextInt(Direction.values().length);
            Direction moveDirection = Direction.values()[directionIndex];
            if (this.moveDelta.x+moveDirection.getDx() > limEast || -(this.moveDelta.x+moveDirection.getDx()) > limWest || this.moveDelta.y+moveDirection.getDy() > limNorth || -(this.moveDelta.y+moveDirection.getDy()) > limSouth) {
                //aca tiene que hacer un refacing para mirar hacia donde estaba caminando
                currentWaitTime = calculateWaitTime();
                timer = 0f;
                return;
            }
            boolean moved = getActor().move(moveDirection);
            if (moved) {
                this.moveDelta.x += moveDirection.getDx();
                this.moveDelta.y += moveDirection.getDy();
            }

            currentWaitTime = calculateWaitTime();
            timer = 0f;
        }
    }

    private float calculateWaitTime() {
        return random.nextFloat() * (moveIntervalMaximum - moveIntervalMinimum) + moveIntervalMinimum;
    }
}


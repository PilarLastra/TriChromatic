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

    private float dx, dy;
    private float limNorth, limSouth, limEast, limWest;

    public LimitedWalkingBehavior(Actor actor, float limNorth, float limSouth, float limEast, float limWest, float moveIntervalMinimum, float moveIntervalMaximum, Random random) {
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
        this.dx = dx;
        this.dy = dy;
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
            if (this.dx+moveDirection.getDx() > limEast || -(this.dx+moveDirection.getDx()) > limWest || this.dy+moveDirection.getDy() > limNorth || -(this.dy+moveDirection.getDy()) > limSouth) {
                currentWaitTime = calculateWaitTime();
                timer = 0f;
                return;
            }
            boolean moved = getActor().move(moveDirection);

            if (moved) {
                this.dx += moveDirection.getDx();
                this.dy += moveDirection.getDy();


            }

            currentWaitTime = calculateWaitTime();
            timer = 0f;
        }
    }

    private float calculateWaitTime() {
        return random.nextFloat() * (moveIntervalMaximum - moveIntervalMinimum) + moveIntervalMinimum;
    }
}


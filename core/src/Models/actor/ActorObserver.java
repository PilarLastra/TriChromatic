package Models.actor;

import Models.Direction;
import Models.actor.Actor;

public interface ActorObserver {

    /**
     * Called from an Actor when he/she is finished moving.
     *
     * @param a				Actor in question
     * @param direction		The direction of the move
     * @param x				Location after the move
     * @param y
     */
    public void actorMoved(Actor a, Direction direction, int x, int y);

    /**
     * Called from an Actor when he/she attempted to move, but was unsuccessful
     * @param a				Actor in question
     * @param direction		The direction of the move
     */
    public void attemptedMove(Actor a, Direction direction);

    public void actorBeforeMoved(Actor a, Direction direction);

}

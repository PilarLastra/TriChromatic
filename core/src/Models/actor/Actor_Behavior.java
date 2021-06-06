package Models.actor;

import Models.actor.Actor;

public  abstract  class Actor_Behavior {

    private Actor actor;

    public Actor_Behavior(Actor actor) {
        this.actor = actor;
    }

    public abstract void update (float delta);

    protected Actor getActor(){
        return actor;
    }
}

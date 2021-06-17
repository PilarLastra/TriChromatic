package power;

import actorPeleador.ActorPeleador;
import battle.Battle;

public abstract class Power {

    protected PowerSpecification spec;

    public Power(PowerSpecification spec) {
        this.spec = spec;
    }

    public void usePower (Battle mechanics, ActorPeleador player, ActorPeleador opponent){
        int damage =  mechanics.calculateDamage(this,player);
        player.applyDamage(damage);


    }

    public abstract String message();


    public String getName(){
        return spec.getName();
    }
    public int getPower() {
        return spec.getPower();
    }
    public float getAccuracy() { return spec.getAccuracy(); }


    public PowerSpecification getPowerSpecification() {
        return spec;
    }


    public abstract Power clone();

}

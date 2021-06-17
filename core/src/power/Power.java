package power;

import actorPeleador.ActorPeleador;
import battle.Battle;

public abstract class Power {

    protected PowerSpecification spec;

    public Power(PowerSpecification spec) {
        this.spec = spec;
    }

    public void usePower (Battle mechanics, ActorPeleador user, ActorPeleador target){
        int damage =  mechanics.calculateDamage(this,user);
        target.applyDamage(damage);


    }



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

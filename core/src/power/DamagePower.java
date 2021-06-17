package power;

import actorPeleador.ActorPeleador;
import battle.Battle;

public class DamagePower extends Power{

    public DamagePower(PowerSpecification spec) {
        super(spec);
    }

    @Override
    public String message() {
        return null;
    }

    @Override
    public void usePower(Battle mechanics, ActorPeleador user, ActorPeleador target) {
     super.usePower(mechanics,user,target);

    }

    @Override
    public Power clone() {
        return null;
    }
}



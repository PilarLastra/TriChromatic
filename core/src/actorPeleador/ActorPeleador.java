package actorPeleador;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGame;
import power.Power;
import power.PowerDatabase;
import power.PowerSpecification;

public class ActorPeleador {

    private String name;

    private int hp;

    private int maxHp;

    private int level;

    private Power [] powers;




    public ActorPeleador(String name,  int level, Power [] powers) {
        this.name = name;
        this.level = level;
        this.hp = 250*level;
        this.powers = powers;
        this.maxHp =hp;


    }


    public String getName() {
        return name;
    }

    public int getMaxHp (){return maxHp;}

    public int getHp() {
        return hp;
    }


    public int getLevel() {
        return level;
    }

    public void applyDamage (int amount){
        hp -=amount;
        if (hp <0){
            hp = 0;
        }
    }

    public boolean isFainted (){
        return  hp ==0;
    }

    public Power getPowers(int index) {
        return  powers[index];
    }

    public void setPowers(int index, Power power) {
        powers[index] = power;
    }

    public PowerSpecification getPowerSpecification (int index){
        return powers[index].getPowerSpecification();
    }



    public static ActorPeleador generatePower (ActorPeleador user, PowerDatabase powerDatabase){


        user.setPowers(0, powerDatabase.getPower(0));
        user.setPowers(1, powerDatabase.getPower(1));
        user.setPowers(2, powerDatabase.getPower(2));
        user.setPowers(3, powerDatabase.getPower(3));


        // y todos los poderes que queramos ponerle
        return user;
    }
}

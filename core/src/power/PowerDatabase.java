package power;

import com.badlogic.gdx.math.Interpolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PowerDatabase {

    private List <Power> powers = new ArrayList<Power>();

    private HashMap<String, Integer> mappings = new HashMap<String, Integer>();

    public PowerDatabase (){
        initializePower();
    }

    private void initializePower(){
        addPower (new DamagePower(
                new PowerSpecification(
                        70,
                        0.9f,
                        "TACKLE",
                        "Charges the foe with a full-body tackle.")
                )

        )
        ;
        addPower (new DamagePower(
                        new PowerSpecification(
                                50,
                                1f,
                                "WATER GUN",
                                "Squirts water to attack the foe.")
                )

        );
        addPower (new DamagePower(
                        new PowerSpecification(
                                100,
                                0.75f,
                                "Dragon Claw",
                                "Hooks and slashes the foe with long, sharp claws.")
                )

        );
        addPower (new DamagePower(
                        new PowerSpecification(
                                150,
                                0.5f,
                                "Scratch",
                                "Scratches the foe with sharp claws.")
                )

        );
    }

    private void addPower (Power power){

        powers.add(power);
        mappings.put(power.getName(),powers.size()-1);
    }

    public Power getPower (String powerName){
        return powers.get(mappings.get(powerName)).clone();
    }

    public Power getPower (int index){
        return powers.get(index);
    }



}

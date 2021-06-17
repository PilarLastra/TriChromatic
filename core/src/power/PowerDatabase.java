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
                        "WHISCAT",
                        "Lanza a su rival una poderosa lata de alimento balanceado para gatos.")
                )

        )
        ;
        addPower (new DamagePower(
                        new PowerSpecification(
                                50,
                                1f,
                                "HERENCIA",
                                "Donde va el padre, va el hijo.")
                )

        );
        addPower (new DamagePower(
                        new PowerSpecification(
                                0,
                                1f,
                                "QUEQUEN",
                                "No hace nada.")
                )

        );
        addPower (new DamagePower(
                        new PowerSpecification(
                                150,
                                0.5f,
                                "CCHALM",
                                "Lanza una bocanada de palabras que pueden confundir al rival.")
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

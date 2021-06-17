package power;

public class PowerSpecification {

    private int power;
    private String name;
    private String description;
    private float accuracy;

    public PowerSpecification(int power, float accuracy,  String name, String description) {
        this.power = power;
        this.accuracy = accuracy;
        this.name = name;
        this.description = description;
    }

    public int getPower() {
        return power;
    }

    public String getName() {
        return name;
    }

    public float getAccuracy(){return accuracy;}

    public String getDescription() {
        return description;
    }
}

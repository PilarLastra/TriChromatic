package Models;

public enum Direction {

    NORTH(0,0.5f),
    EAST(0.5f,0),
    SOUTH(0,-0.5f),
    WEST(-0.5f,0),
    ;


    private float dx, dy;

    private Direction (float dx, float dy){
        this.dx = dx;
        this.dy = dy;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public static Direction getOpposite(Direction dir) {
        switch (dir) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }
        return null;
    }

}

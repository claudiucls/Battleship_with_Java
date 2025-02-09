package battleship;

public class Submarine extends Ship{

    static final int shipLength = 3;
    static final String type = "Submarine";

    public Submarine() {
        super();
        super.shipLength = shipLength;
        super.type = type;
    }
}

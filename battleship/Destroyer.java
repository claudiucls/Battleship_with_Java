package battleship;

public class Destroyer extends Ship {

    static final int shipLength = 2;
    static final String type = "Destroyer";

    public Destroyer() {
        super();
        super.shipLength = shipLength;
        super.type = type;
    }
}

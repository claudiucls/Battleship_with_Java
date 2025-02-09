package battleship;

public class Cruiser extends Ship{
    static final int shipLength =3;
    static final String type = "Cruiser";

    public Cruiser() {
        super();
        super.shipLength = shipLength;
        super.type = type;
    }
}

package battleship;

import java.util.ArrayList;

public class Player {

    String name;
    char[][] battlefield = new char[11][11];
    ArrayList<Ship> allShips = new ArrayList<>();
    ArrayList<String> hitOnTargets = new ArrayList<>();
    ArrayList<String> allpoints = new ArrayList<>();

    Ship aircraftCarrier = new Aircraft_Carrier();
    Ship battleship = new Battleship();
    Ship submarine = new Submarine();
    Ship cruiser = new Cruiser();
    Ship destroyer = new Destroyer();

    Player(String name){
        this.name =name;
    }

}

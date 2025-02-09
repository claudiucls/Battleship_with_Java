package battleship;

import java.util.Scanner;

public class Main {


    static Scanner sc = new Scanner(System.in);
    static private boolean weHaveAWinner = false;
    static private int turn = 0;
    static Player player1 = new Player("Player 1");
    static Player player2 = new Player("Player 2");

    public static void main(String[] args) {


        System.out.println("Player 1, place your ships on the game field\n");

        showEmptyField(player1, true);
        createShips(player1, player1.aircraftCarrier);
        createShips(player1, player1.battleship);
        createShips(player1, player1.submarine);
        createShips(player1, player1.cruiser);
        createShips(player1, player1.destroyer);


        System.out.println("Press Enter and pass the move to another player\n...");
        sc.nextLine();
        System.out.println("Player 2, place your ships on the game field\n");

        showEmptyField(player2, true);
        createShips(player2, player2.aircraftCarrier);
        createShips(player2, player2.battleship);
        createShips(player2, player2.submarine);
        createShips(player2, player2.cruiser);
        createShips(player2, player2.destroyer);

        // The game starts!

        do {
            System.out.println("Press Enter and pass the move to another player\n...");
            sc.nextLine();
            if (turn % 2 == 0) {
                showEmptyField(player1, false);
                System.out.println("---------------------");
                takeShot(player1, player2);
            } else {
                showEmptyField(player2, false);
                System.out.println("---------------------");
                takeShot(player2, player1);
            }
            turn++;
        } while (!weHaveAWinner);
        sc.close();
    }
    // This method initialize each players battlefield with predefined row from A to J
    // and columns from 1 to 10
    private static void showEmptyField(Player player, boolean gameStart) {
        char[][] battlefield = new char[11][11];
        int column = 1;
        char letter = 'A';
        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield[i].length; j++) {
                if (i == 0) {
                    if (j == 0) {
                        System.out.print("  ");
                        battlefield[i][j] = ' ';
                    } else {
                        System.out.printf("%d ", column);
                        battlefield[i][j] = (char) (column + 48);
                        column++;
                    }
                } else if (j == 0) {
                    System.out.printf("%c ", letter);
                    battlefield[i][j] = letter;
                    letter++;
                } else {
                    battlefield[i][j] = '\u0000';
                    System.out.print("~ ");
                }
            }
            System.out.println();
        }
        // initialize battlefield for both players
        if (gameStart) {
            if (player.equals(player1)) player1.battlefield = battlefield;
            else player2.battlefield = battlefield;
        }
    }

    // show players's battle field with each changes
    // the ships can be hidden under "fog" '~' so other player can see them
    private static void showField(Player player, boolean showFog) {
        for (int i = 0; i < player.battlefield.length; i++) {
            for (int j = 0; j < player.battlefield[i].length; j++) {
                if (player.battlefield[i][j] == '\u0000') {
                    System.out.printf("%c ", '~');
                } else {
                    if (i == 0 && j == 0) System.out.print("  ");
                    else if (i == 0 && j == 10) System.out.print("10");
                    else {
                        if (showFog) {
                            System.out.printf("%c ", player.battlefield[i][j] == 'O' ? '~' : player.battlefield[i][j]);
                        } else
                            System.out.printf("%c ", player.battlefield[i][j]);
                    }
                }
            }
            System.out.println();
        }
    }

    // The method creates and add one type of each ship in the battlefield
    private static void createShips(Player player, Ship ship) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.type, ship.shipLength);
        String s;
        FIRST:
        while (true) {
            s = sc.nextLine().toUpperCase();
            if (!ship.checkInputFormat(s)) {
                System.out.println("Error! Not a good format! Try again:");
                continue;
            } else if (!ship.checkInTheSameLine(s)) {
                continue;
            }

            ship.setPoints(s);

            if (player.allpoints.isEmpty()) {
                player.allpoints.addAll(ship.points);
            } else {
                for (String point : ship.points) {
                    if (isAdjacent(point, player)) {
                        System.out.println("Error! You placed it too close to another one. Try again:");
                        ship.points.clear();
                        continue FIRST;
                    }
                }
                player.allpoints.addAll(ship.points);
            }
            ship.addShip(player.battlefield);
            player.allShips.add(ship);
            showField(player, false);
            break;
        }
    }

    // This method doesn't allow player to place a ship next to other
    public static boolean isAdjacent(String point, Player player) {
        // Extract row letter and column number from the point (e.g., "F3" -> row = 'F', col = 3)
        char row = point.charAt(0);
        int col = Integer.parseInt(point.substring(1));

        // Directions for neighbors: [up, down, left, right, top-left, top-right, bottom-left, bottom-right]
        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};  // Row movements
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};  // Column movements

        // Check if the new point is adjacent to any predefined point
        for (String predefined : player.allpoints) {
            char predefinedRow = predefined.charAt(0);
            int predefinedCol = Integer.parseInt(predefined.substring(1));

            // Check all 8 possible neighboring positions
            for (int i = 0; i < dx.length; i++) {
                int newRow = (char) (row + dx[i]);  // Move row up/down (convert char to char based on direction)
                int newCol = col + dy[i];  // Move column left/right

                // Ensure the new position is within bounds (rows A-J, columns 1-10)
                if (newRow >= 'A' && newRow <= 'J' && newCol >= 1 && newCol <= 10) {
                    // Check if the new position is the same as the predefined point
                    if (newRow == predefinedRow && newCol == predefinedCol) {
                        return true;  // The point is adjacent to a predefined point
                    }
                }
            }
        }

        return false;  // No adjacency found
    }

    // This method take a shot from player 1 to player 2
    // Here also it decides if it's a hit or miss, and if an one ship is sank
    // If all the ships are sank ... then we have a winner
    public static void takeShot(Player player1, Player player2) {
        showField(player1, false);
        System.out.printf("%s, it's your turn!%n", player1.name);
        String s = "";
        int row = 0;
        int col = 0;
        boolean correct = true;
        while (correct) {
            s = sc.nextLine();
            if (!s.toUpperCase().matches("^[A-J](10|[1-9])$")) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            correct = false;
            row = Character.toLowerCase(s.charAt(0)) - 'a' + 1;
            col = Integer.parseInt(s.replaceAll("[^0-9]", ""));
        }
        FIRST:
        while (true) {
            if (player2.hitOnTargets.contains(s)) {
                System.out.println("You hit a ship!"); // This shows if the point was already hit
            } else {
                for (Ship ship : player2.allShips) {
               /* System.out.println(s + "/////////////////");
                System.out.println(ship.points);*/
                    if (ship.points.contains(s.toUpperCase())) {
                        player2.battlefield[row][col] = 'X';
                        player1.battlefield[row][col] = 'X';
                        player2.hitOnTargets.add(s);
                        ship.points.remove(s.toUpperCase());
                        player2.allpoints.remove(s.toUpperCase());
                        if (ship.points.isEmpty() && !player2.allpoints.isEmpty()) {
                            System.out.println("You sank a ship! Specify a new target:");
                            break FIRST;
                        }
                        if (player2.allpoints.isEmpty()) {
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            Main.weHaveAWinner = true;
                            break FIRST;
                        } else {
                            System.out.println("You hit a ship!");
                            break FIRST;
                        }
                    }
                }

            }
            player2.battlefield[row][col] = 'M';
            System.out.println("You missed!");
            break FIRST;
        }
    }
}
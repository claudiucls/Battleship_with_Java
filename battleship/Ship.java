package battleship;

import java.util.ArrayList;

public class Ship {

    protected int shipLength;
    protected String start;
    protected String end;
    protected String type;
    protected ArrayList<String> points = new ArrayList<>();
    public void addShip(char[][] field) {

        int rowStart = Character.toLowerCase(start.charAt(0)) - 'a' + 1;
        int columnStart = Integer.parseInt(start.replaceAll("[^0-9]", ""));
        int rowEnd = Character.toLowerCase(end.charAt(0)) - 'a' + 1;
        int columnEnd = Integer.parseInt(end.replaceAll("[^0-9]", ""));
        for (int i = 0; i < shipLength; i++) {
            if (rowStart == rowEnd) {   // position is horizontal
                if (columnStart < columnEnd) {
                    field[rowStart][columnStart + i] = 'O';
                } else {
                    field[rowStart][columnStart - i] = 'O';
                }
            } else if (columnStart == columnEnd) { //position is vertical
                if (rowStart < rowEnd) {
                    field[rowStart + i][columnStart] = 'O';
                } else {
                    field[rowStart - i][columnEnd] = 'O';
                }
            }
        }
    }



    public boolean checkInputFormat(String s) {
        return s.matches("[A-J]([1-9]|10) [A-J]([1-9]|10)");
    }

    public boolean checkInTheSameLine(String s) {

        String[] parts = s.split(" ");
        if (parts.length != 2) {
            return false;
        }
        start = parts[0];
        end = parts[1];

        char row1 = start.charAt(0);
        int col1 = Integer.parseInt(start.substring(1));

        char row2 = end.charAt(0);
        int col2 = Integer.parseInt(end.substring(1));
        if (row1 == row2) {
            if (shipLength != Math.abs(col2 - col1) + 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:%n", type);
                return false;
            } else {

                return true;
            }
        } else if (col1 == col2) {
            if (shipLength != Math.abs(row2 - row1) + 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:%n", type);
                return false;
            } else{

                return true;
            }
        }
        System.out.println("Error! Wrong ship location! Try again:");
        return false;
    }

    public void setPoints(String s) {
        String[] parts = s.split(" ");

        start = parts[0];
        end = parts[1];

        char row1 = start.charAt(0);
        int col1 = Integer.parseInt(start.substring(1));

        char row2 = end.charAt(0);
        int col2 = Integer.parseInt(end.substring(1));

        if(row1==row2){      // if line is horizontal
            if(col1<col2){     //ascending
                for(int i = col1;i<=col2;i++){
                    points.add(""+row1+i);
                }
            } else {     // descending
                for(int i = col1;i>=col2;i--){
                    points.add(""+row1+i);
                }
            }
        } else if(col1 == col2){ // if line is vertical
            if(row1<row2){     //ascending
                for(char i = row1;i<=row2;i++){
                    points.add(""+i+col1);
                }
            } else {     // descending
                for(char i = row1;i>=row2;i--){
                    points.add(""+i+col1);
                }
            }
        }
    }


}

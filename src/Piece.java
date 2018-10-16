/**
 * Created by Jonathan on 10/15/2018.
 */

abstract class Piece {

//all piece classes extend the base abstract class
    private String location;
    private String color;

    public abstract boolean checkValidMove();
    public abstract boolean checkExternalRing();

    public Piece(String loc, String color){
        this.location = loc;
        this.color = color;
    }



    public static void main(String[] args) {

        //test piece classes
        //All pieces must:
            //-check for which ring they currently are in
            //-check for where the rest of their players pieces are located
            //-check and update valid moves after reaching rebounding conditions
    }





        }
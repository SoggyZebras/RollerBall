/**
 * Created by Jonathan on 10/15/2018.
 */

abstract class Piece_Abstract{

//all piece classes extend the base abstract class
    private String location;
    private String color;

    public abstract boolean checkValidMove();
    public abstract boolean checkExternalRing();

    public Piece_Abstract(String loc, String color){
        this.location = loc;
        this.color = color;
    }



    public static void main(String[] args) {

        //test piece classes
    }





        }
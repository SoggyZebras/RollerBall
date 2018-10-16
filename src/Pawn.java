/**
 * Created by Jonathan on 10/15/2018.
 */
public class Pawn extends Piece {

    private String location;
    private String color;

    public Pawn(String location, String color){
        super(location, color);
    }
    public String getLocation(){return this.location;}
    public String getColor(){return this.color;}
    public void setLocation(String loc){this.location = loc;}



    public boolean checkValidMove(){

        //Switch statement for available options?


        return false;}
    public boolean checkExternalRing(){return false;}






}
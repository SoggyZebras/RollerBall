package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

import java.io.*;
import java.util.Base64;

public class ClientMakeMove implements Event {

    //Information to be serialized or deserialized
    private int message_type;
    private Location to;
    private Location from;
    private int gameID;

    //Sending message constructor

    /**
     *
     * @param from
     * @param to
     */
    public ClientMakeMove(Location from, Location to, int id){

        this.message_type = eClient_Make_Move;
        this.to = to;
        this.from =from;
        this.gameID = id;
    }

    //Recieving message constructor

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ClientMakeMove(String input) throws IOException, ClassNotFoundException {

        byte[] data = Base64.getDecoder().decode(input);
        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
        // deserialize the objects into their proper local variables

        this.message_type = oin.readInt();
        this.gameID = oin.readInt();
        this.to = (Location) oin.readObject();
        this.from = (Location) oin.readObject();



        // Close streams
        oin.close();

    }


    @Override
    public String getFile() throws IOException {

        // Create a new String, file output stream, object output stream
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(ostream);

        // Take the local variables and serialize them into a file
        oout.writeInt(this.message_type);
        oout.writeInt(gameID);
        oout.writeObject(this.to);
        oout.writeObject(this.from);

        //flush the objects to the stream and close the streams
        oout.flush();
        oout.close();

        return Base64.getEncoder().encodeToString(ostream.toByteArray());
    }

    @Override
    public int getType() {
        return this.message_type;
    }

    /**
     *
     * @return Location
     */
    public Location getTo(){
        return to;
    }

    /**
     *
     * @return Location
     */
    public Location getFrom(){
        return from;
    }

    public int getGameID() { return gameID;}
}

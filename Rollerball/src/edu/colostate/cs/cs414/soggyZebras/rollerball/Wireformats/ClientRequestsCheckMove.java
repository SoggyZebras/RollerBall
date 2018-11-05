package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

import java.io.*;

import static edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Protocol.Client_Request_Check_Move;

public class ClientRequestsCheckMove implements Event{

    //Information to be serialized or deserialized
    private String message_type;
    private Location place;

    /**
     *
     * @param p
     */
    public ClientRequestsCheckMove(Location p) {

        this.message_type = Client_Request_Check_Move;
        this.place = p;
    }

    /**
     *
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    protected ClientRequestsCheckMove(String filename) throws IOException, ClassNotFoundException {

        // Create a file input stream and a object input stream to read the incomming message
        FileInputStream fileStream = new FileInputStream(filename);
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(fileStream));

        // deserialize the objects into their proper local variables

        this.message_type = (String) oin.readObject();
        this.place = (Location) oin.readObject();



        // Close streams
        oin.close();
        fileStream.close();
    }

    @Override
    public String getFile() throws IOException {

        // Create a new String, file output stream, object output stream
        String filename = this.message_type;
        FileOutputStream fileStream = new FileOutputStream(filename);
        ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(fileStream));

        // Take the local variables and serialize them into a file
        oout.writeObject(filename);
        oout.writeObject(this.place);

        //flush the objects to the stream and close the streams
        oout.flush();
        oout.close();
        fileStream.close();
        return filename;
    }

    @Override
    public String getType() {
        return this.message_type;
    }

    public Location getPlace(){
        return this.place;
    }


}



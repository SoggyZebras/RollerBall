package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.*;
import java.util.Base64;

public class ClientRequestGameState implements Event{

    //Information to be serialized or deserialized
    private int message_type;
    private int gameID;

    public ClientRequestGameState(int id) { this.message_type = eClient_Request_Game_State; this.gameID = id; }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    protected ClientRequestGameState(String input) throws IOException, ClassNotFoundException {

        byte[] data = Base64.getDecoder().decode(input);
        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
        // deserialize the objects into their proper local variables

        this.message_type = oin.readInt();
        this.gameID = oin.readInt();

        // Close streams
        oin.close();

    }

    @Override
    public String getFile() throws IOException {

        // Create a new String, file output stream, object output stream
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(ostream);

        oout.writeInt(this.message_type);
        oout.writeInt(gameID);

        //flush the objects to the stream and close the streams
        oout.flush();
        oout.close();

        return Base64.getEncoder().encodeToString(ostream.toByteArray());

    }

    @Override
    public int getType() {
        return this.message_type;
    }

    public int getGameID() { return this.gameID; }

}

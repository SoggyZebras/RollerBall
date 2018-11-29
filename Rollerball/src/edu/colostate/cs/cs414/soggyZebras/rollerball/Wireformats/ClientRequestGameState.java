package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.*;

public class ClientRequestGameState implements Event{

    //Information to be serialized or deserialized
    private String message_type;
    private int gameID;

    public ClientRequestGameState(int id) { this.message_type = Client_Request_Game_State; this.gameID = id; }

    /**
     *
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    protected ClientRequestGameState(String filename) throws IOException, ClassNotFoundException {

        // Create a file input stream and a object input stream to read the incomming message
        FileInputStream fileStream = new FileInputStream(filename);
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(fileStream));

        this.message_type = (String) oin.readObject();
        this.gameID = oin.readInt();

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

        oout.writeObject(filename);
        oout.writeInt(gameID);

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

    public int getGameID() { return this.gameID; }

}

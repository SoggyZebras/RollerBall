package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.*;

public class ClientRequestGameState implements Event{

    //Information to be marshalled or unmarshalled
    private byte message_type;


    public ClientRequestGameState() {
        this.message_type = Client_Request_Game_State;
    }

    protected ClientRequestGameState(byte[] marshalledBytes) throws IOException{

        // Create a byte input stream and a data input stream to read the incomming message
        ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

        this.message_type = din.readByte();

        // Close streams
        baInputStream.close();
        din.close();
    }

    @Override
    public byte[] getBytes() throws IOException {

        // Create a new byte[], byte output stream, data output stream
        byte[] marshalledBytes = null;
        ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(baOutputStream));

        dout.writeByte(this.message_type);

        //flush the bits to the stream and close the streams
        dout.flush();
        marshalledBytes = baOutputStream.toByteArray();

        baOutputStream.close();
        dout.close();
        return marshalledBytes;

    }

    @Override
    public byte getType() {
        return this.message_type;
    }

}

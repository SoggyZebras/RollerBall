package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

import java.io.*;

public class ClientMakeMove implements Event {

    //Information to be marshalled and unmarshalled
    private byte message_type;
    private int rowTo;
    private int rowFrom;
    private int colTo;
    private int colFrom;

    //Sending message constructor
    public ClientMakeMove(Location from, Location to){

        this.message_type = Client_Make_Move;
        rowTo = to.getRow();
        colTo = to.getCol();
        rowFrom = from.getRow();
        colFrom = from.getCol();
    }

    //Recieving message constructor
    public ClientMakeMove(byte[] marshalledBytes) throws IOException {

        // Create a byte input stream and a data input stream to read the incomming message
        ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
        DataInputStream din = new DataInputStream(new BufferedInputStream(baInputStream));

        // Unmarshall the bytes into their proper local variables
        this.message_type = din.readByte();
        this.rowTo = din.readInt();
        this.colTo = din.readInt();
        this.rowFrom = din.readInt();
        this.colFrom = din.readInt();

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

        // Take the local variables and Marsall them into bytes
        dout.writeByte(this.message_type);
        dout.writeInt(this.rowTo);
        dout.writeInt(this.colTo);
        dout.writeInt(this.rowFrom);
        dout.writeInt(this.colFrom);

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

    public Location getTo(){
        return new Location(this.rowTo,this.colTo);
    }

    public Location getFrom(){
        return new Location(this.rowFrom, this.colFrom);
    }
}

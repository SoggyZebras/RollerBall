package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;


import java.io.*;
import java.util.Map;

public class ServerRespondsGameState implements Event, Serializable{

  //Information to be marshalled or unmarshalled
  private byte message_type;
  private Map<Location,Piece> board;

  public ServerRespondsGameState(Map<Location,Piece> m) {
    this.message_type = Server_Responds_Game_State;
    this.board = m ;
  }

  public ServerRespondsGameState(byte[] marshalledBytes) throws IOException, ClassNotFoundException {

    // Create a byte input stream and a data input stream to read the incomming message
    ByteArrayInputStream baInputStream = new ByteArrayInputStream(marshalledBytes);
    ObjectInputStream din = new ObjectInputStream(new BufferedInputStream(baInputStream));

    this.message_type = din.readByte();
    this.board = (Map<Location,Piece>) din.readObject();

    // Close streams
    baInputStream.close();
    din.close();
  }

  @Override
  public byte[] getBytes() throws IOException {

    // Create a new byte[], byte output stream, data output stream
    byte[] marshalledBytes = null;
    ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream dout = new ObjectOutputStream(new BufferedOutputStream(baOutputStream));

    dout.writeByte(this.message_type);
    dout.writeObject(this.board);

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

  public Map<Location,Piece>getMap(){
    return this.board;
  }

}

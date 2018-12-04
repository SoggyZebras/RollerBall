package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;


import java.io.*;
import java.util.Base64;
import java.util.Map;

public class ServerRespondsGameState implements Event {

  //Information to be serialized or deserialized
  private int message_type;
  private int gameID;
  private Map<Location,Piece> board;

  /**
   *
   * @param m
   */
  public ServerRespondsGameState(Map<Location,Piece> m, int id) {

    this.message_type = eServer_Responds_Game_State;
    this.gameID = id;
    this.board = m ;
  }

  /**
   *
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public ServerRespondsGameState(String input) throws IOException, ClassNotFoundException {

    byte[] data = Base64.getDecoder().decode(input);
    ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
    // deserialize the objects into their proper local variables


    this.message_type = oin.readInt();
    this.gameID = oin.readInt();
    this.board = (Map<Location,Piece>) oin.readObject();



    // Close streams
    oin.close();

  }

  @Override
  public String getFile() throws IOException {

    // Create a new String, file output stream, object output stream
    ByteArrayOutputStream ostream = new ByteArrayOutputStream();
    ObjectOutputStream oout = new ObjectOutputStream(ostream);


    oout.writeInt(this.message_type);
    oout.writeInt(this.gameID);
    oout.writeObject(this.board);

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
   * @return Map<Location,Piece>
   */
  public Map<Location,Piece>getMap(){
    return this.board;
  }

  public int getGameID(){ return this.gameID; }

}

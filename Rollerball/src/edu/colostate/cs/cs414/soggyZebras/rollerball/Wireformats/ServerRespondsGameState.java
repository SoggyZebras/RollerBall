package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;


import java.io.*;
import java.util.Map;

public class ServerRespondsGameState implements Event {

  //Information to be serialized or deserialized
  private String message_type;
  private Map<Location,Piece> board;

  /**
   *
   * @param m
   */
  public ServerRespondsGameState(Map<Location,Piece> m) {

    this.message_type = Server_Responds_Game_State;
    this.board = m ;
  }

  /**
   *
   * @param filename
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public ServerRespondsGameState(String filename) throws IOException, ClassNotFoundException {

    // Create a file input stream and a object input stream to read the incomming message
    FileInputStream fileStream = new FileInputStream(filename);
    ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(fileStream));


    this.message_type = (String) oin.readObject();
    this.board = (Map<Location,Piece>) oin.readObject();



    // Close streams
    oin.close();
    fileStream.close();
  }

  @Override
  public String getFile() throws IOException {

    // Create a new String, file output stream, object output stream
    FileOutputStream fileStream = new FileOutputStream(this.message_type);
    ObjectOutputStream oout = new ObjectOutputStream(new BufferedOutputStream(fileStream));

    oout.writeObject(this.message_type);
    oout.writeObject(this.board);

    //flush the objects to the stream and close the streams
    oout.flush();
    oout.close();
    fileStream.close();
    return this.message_type;

  }

  @Override
  public String getType() {
    return this.message_type;
  }

  /**
   *
   * @return Map<Location,Piece>
   */
  public Map<Location,Piece>getMap(){
    return this.board;
  }

}

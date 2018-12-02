package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import java.io.*;

import static edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Protocol.Client_Sends_Login;

public class ServerRespondsRefresh implements Event {

  //Information to be serialized or deserialized
  private String message_type;
  private User user;


  //Sending message constructor

  public ServerRespondsRefresh(User u){

    this.message_type = Server_Responds_Refresh;
    this.user = u;


  }

  //Recieving message constructor

  /**
   *
   * @param filename
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public ServerRespondsRefresh(String filename) throws IOException, ClassNotFoundException {

    // Create a file input stream and a object input stream to read the incomming message
    FileInputStream fileStream = new FileInputStream(filename);
    ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(fileStream));

    // deserialize the objects into their proper local variables

    this.message_type = (String) oin.readObject();
    this.user = (User) oin.readObject();



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

  public User getUser() { return this.user; }
}

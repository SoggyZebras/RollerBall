package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;

public class ClientRequestUserList implements Event{

  //Information to be serialized or deserialized
  private int message_type;

  //Sending message constructor


  public ClientRequestUserList(){

    this.message_type = eClient_Request_User_List;
  }

  //Recieving message constructor

  /**
   *
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public ClientRequestUserList(String input) throws IOException, ClassNotFoundException {


    byte[] data = Base64.getDecoder().decode(input);
    ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
    // deserialize the objects into their proper local variables


    this.message_type = oin.readInt();

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

    //flush the objects to the stream and close the streams
    oout.flush();
    oout.close();

    return Base64.getEncoder().encodeToString(ostream.toByteArray());

  }

  @Override
  public int getType() {
    return this.message_type;
  }



}

package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;

import java.io.*;



public class ServerRespondsLogin implements Event {


    //Information to be serialized or deserialized
    private String message_type;
    private User user;
    private String reject_reason;


    //Sending message constructor

    public ServerRespondsLogin(User u,String reason){

        this.message_type = Server_Responds_Login;
        this.user = u;
        this.reject_reason = reason;

    }

    //Recieving message constructor

    /**
     *
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ServerRespondsLogin(String filename) throws IOException, ClassNotFoundException {

        // Create a file input stream and a object input stream to read the incomming message
        FileInputStream fileStream = new FileInputStream(filename);
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(fileStream));

        // deserialize the objects into their proper local variables

        this.message_type = (String) oin.readObject();
        this.user = (User) oin.readObject();
        this.reject_reason = (String) oin.readObject();



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
        oout.writeObject(this.user);
        oout.writeObject(this.reject_reason);

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

    public String getReject_reason() { return this.reject_reason;}

}

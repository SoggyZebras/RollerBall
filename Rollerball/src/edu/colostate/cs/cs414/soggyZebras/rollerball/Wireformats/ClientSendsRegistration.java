package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.*;

import static edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats.Protocol.Client_Sends_Login;

public class ClientSendsRegistration implements Event {

    //Information to be serialized or deserialized
    private String message_type;
    private String username;
    private String password;


    //Sending message constructor

    public ClientSendsRegistration(String username, String password){

        this.message_type = Client_Sends_Registration;
        this.username = username;
        this.password = password;

    }

    //Recieving message constructor

    /**
     *
     * @param filename
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ClientSendsRegistration(String filename) throws IOException, ClassNotFoundException {

        // Create a file input stream and a object input stream to read the incomming message
        FileInputStream fileStream = new FileInputStream(filename);
        ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(fileStream));

        // deserialize the objects into their proper local variables

        this.message_type = (String) oin.readObject();
        this.username = (String) oin.readObject();
        this.password = (String) oin.readObject();


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
        oout.writeObject(this.username);
        oout.writeObject(this.password);

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

    public String getUsername() { return this.username; }

    public String getPassword() { return this.password; }
}

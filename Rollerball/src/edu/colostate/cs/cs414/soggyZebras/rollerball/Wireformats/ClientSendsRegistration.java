package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class ClientSendsRegistration implements Event {

    //Information to be serialized or deserialized
    private int message_type;
    private String username;
    private String password;
    private String email;


    //Sending message constructor

    public ClientSendsRegistration(String username, String password, String email){

        this.message_type = eClient_Sends_Registration;
        this.username = username;
        this.password = password;
        this.email = email;

    }

    //Recieving message constructor

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ClientSendsRegistration(String input) throws IOException, ClassNotFoundException {


        byte[] data = Base64.getDecoder().decode(input);
        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
        // deserialize the objects into their proper local variables

        this.message_type = oin.readInt();
        this.username = (String) oin.readObject();
        this.password = (String) oin.readObject();
        this.email = (String) oin.readObject();

        // Close streams
        oin.close();


    }


    @Override
    public String getFile() throws IOException {



        // Create a new String, file output stream, object output stream
        ByteArrayOutputStream oout = new ByteArrayOutputStream();
        ObjectOutputStream ostream = new ObjectOutputStream(oout);

        // Take the local variables and serialize them into a file
        ostream.writeInt(this.message_type);
        ostream.writeObject(this.username);
        ostream.writeObject(this.password);
        ostream.writeObject(this.email);

        //flush the objects to the stream and close the streams
        ostream.flush();
        ostream.close();


        return Base64.getEncoder().encodeToString(oout.toByteArray());
    }

    @Override
    public int getType() {
        return this.message_type;
    }

    public String getUsername() { return this.username; }

    public String getPassword() { return this.password; }

    public String getEmail() { return this.email; }
}

package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.*;
import java.util.Base64;

public class ClientSendsLogout implements Event{

        //Information to be serialized or deserialized
        private int message_type;
        int uID;



        //Sending message constructor

        public ClientSendsLogout(int uID){

            this.message_type = eClient_Sends_Logout;
            this.uID = uID;

        }

        //Recieving message constructor

        /**
         *
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public ClientSendsLogout(String input) throws IOException, ClassNotFoundException {

          byte[] data = Base64.getDecoder().decode(input);
          ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
          // deserialize the objects into their proper local variables

            this.message_type = oin.readInt();
            this.uID = oin.readInt();


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
            oout.writeInt(this.uID);

            //flush the objects to the stream and close the streams
            oout.flush();
            oout.close();

          return Base64.getEncoder().encodeToString(ostream.toByteArray());

        }

        @Override
        public int getType() {
            return this.message_type;
        }

        public int getuID() { return this.uID;}

}

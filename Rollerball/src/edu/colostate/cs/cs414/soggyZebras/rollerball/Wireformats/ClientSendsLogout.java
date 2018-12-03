package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.*;

public class ClientSendsLogout implements Event{

        //Information to be serialized or deserialized
        private String message_type;
        int uID;



        //Sending message constructor

        public ClientSendsLogout(int uID){

            this.message_type = Client_Sends_Logout;
            this.uID = uID;

        }

        //Recieving message constructor

        /**
         *
         * @param filename
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public ClientSendsLogout(String filename) throws IOException, ClassNotFoundException {

            // Create a file input stream and a object input stream to read the incomming message
            FileInputStream fileStream = new FileInputStream(filename);
            ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(fileStream));

            // deserialize the objects into their proper local variables

            this.message_type = (String) oin.readObject();
            this.uID = oin.readInt();


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
            oout.writeInt(this.uID);

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

        public int getuID() { return this.uID;}

}

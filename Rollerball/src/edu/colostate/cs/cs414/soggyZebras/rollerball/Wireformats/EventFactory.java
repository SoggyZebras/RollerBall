package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SelectionKey;

public class EventFactory implements Protocol {
    //This class handles the delegation of incoming messages
    //EventFactory is a singleton class as we only need one for the client

    private EventFactory(){}

    public synchronized static void work(byte[] message, Node node, Socket socket) throws ClassNotFoundException {
        // Depending on what type of message has arrived, perfom an action

        try {
            switch (message[0]) {

                case Client_Make_Move: System.out.println("client make move");node.onEvent(new ClientMakeMove(message), socket);break
                    ;
                case Client_Request_Game_State: node.onEvent(new ClientRequestGameState(message), socket);break
                    ;
                case Server_Responds_Game_State: System.out.println("server send move");node.onEvent(new ServerRespondsGameState(message), socket);break
                    ;
                case Client_Sends_Register:

                case Client_Sends_Login:

                case Client_Sends_Game_Invite:

                case Client_Sends_Get_History:

                default:

            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }



}

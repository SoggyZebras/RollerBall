package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.IOException;
import java.net.Socket;

public class EventFactory implements Protocol {
    //This class handles the delegation of incoming messages
    //EventFactory is a singleton class as we only need one for the client

    private EventFactory(){}

    /**
     *
     * @param filename
     * @param node
     * @param socket
     * @throws ClassNotFoundException
     */
    public synchronized static void work(String filename, Node node, Socket socket) throws ClassNotFoundException {
        // Depending on what type of message has arrived, perfom an action

        try {
            switch (filename) {

                case Server_Sends_Connect: node.onEvent(new ServerSendsConnect(filename),socket);break;

                case Client_Make_Move: node.onEvent(new ClientMakeMove(filename), socket);break
                    ;
                case Client_Request_Check_Move: node.onEvent(new ClientRequestsCheckMove(filename),socket);break
                    ;
                case Server_Responds_Check_Move: node.onEvent(new ServerRespondsCheckMove(filename),socket);break
                    ;
                case Client_Request_Game_State: node.onEvent(new ClientRequestGameState(filename), socket);break
                    ;
                case Server_Responds_Game_State: node.onEvent(new ServerRespondsGameState(filename), socket);break
                    ;
                case Server_Responds_Invite: node.onEvent(new ServerRespondsInvite(filename), socket);break;

                case Server_Sends_Invite: node.onEvent(new ServerSendsInvite(filename), socket);break;

                case Client_Sends_Invite: node.onEvent(new ClientSendsInvite(filename), socket);break;

                case Client_Responds_Invite: node.onEvent(new ClientRespondsInvite(filename), socket);break;

                case Server_Responds_Registration:

                case Client_Sends_Registration:

                case Client_Sends_Login:

                case Client_Sends_Invite_Refresh:

                case Server_Responds_Invite_Refresh:

                default:

            }
        } catch(IOException e){
            System.out.println("IO Exception in Event Factory");
            System.out.println(e.getMessage());
        }
    }



}

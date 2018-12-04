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
        System.out.println(filename);
        try {
            switch (filename) {

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

                case Server_Responds_Registration: node.onEvent(new ServerRespondsRegistration(filename), socket);break;

                case Client_Sends_Registration: node.onEvent(new ClientSendsRegistration(filename), socket);break;

                case Client_Sends_Login: node.onEvent(new ClientSendsLogin(filename), socket);break;

                case Server_Responds_Login: node.onEvent(new ServerRespondsLogin(filename),socket);break;

                case Client_Sends_Deregister: node.onEvent(new ClientSendsDeregister(filename),socket);break;

                case Server_Responds_Deregister: node.onEvent(new ServerRespondsDeregister(filename),socket);break;

                case Client_Sends_Logout: node.onEvent(new ClientSendsLogout(filename),socket);break;

                default:

            }
        } catch(IOException e) {
            System.out.println("IO Exception in Event Factory");
            e.printStackTrace();
        }
    }



}

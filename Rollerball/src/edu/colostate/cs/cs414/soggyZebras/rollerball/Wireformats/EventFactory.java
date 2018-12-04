package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

import java.io.IOException;
import java.net.Socket;

public class EventFactory implements Protocol {
    //This class handles the delegation of incoming messages
    //EventFactory is a singleton class as we only need one for the client

    private EventFactory(){}

    /**
     *
     * @param node
     * @param socket
     * @throws ClassNotFoundException
     */
    public synchronized static void work(String data,int type, Node node, Socket socket) throws ClassNotFoundException {
        // Depending on what type of message has arrived, perfom an action
        try {
            switch (type) {

                case eClient_Make_Move: node.onEvent(new ClientMakeMove(data), socket);break
                    ;
                case eClient_Request_Check_Move: node.onEvent(new ClientRequestsCheckMove(data),socket);break
                    ;
                case eServer_Responds_Check_Move: node.onEvent(new ServerRespondsCheckMove(data),socket);break
                    ;
                case eClient_Request_Game_State: node.onEvent(new ClientRequestGameState(data), socket);break
                    ;
                case eServer_Responds_Game_State: node.onEvent(new ServerRespondsGameState(data), socket);break
                    ;
                case eServer_Responds_Invite: node.onEvent(new ServerRespondsInvite(data), socket);break;

                case eServer_Sends_Invite: node.onEvent(new ServerSendsInvite(data), socket);break;

                case eClient_Sends_Invite: node.onEvent(new ClientSendsInvite(data), socket);break;

                case eClient_Responds_Invite: node.onEvent(new ClientRespondsInvite(data), socket);break;

                case eServer_Responds_Registration: node.onEvent(new ServerRespondsRegistration(data), socket);break;

                case eClient_Sends_Registration: node.onEvent(new ClientSendsRegistration(data), socket);break;

                case eClient_Sends_Login: node.onEvent(new ClientSendsLogin(data), socket);break;

                case eServer_Responds_Login: node.onEvent(new ServerRespondsLogin(data),socket);break;

                case eClient_Sends_Deregister: node.onEvent(new ClientSendsDeregister(data),socket);break;

                case eServer_Responds_Deregister: node.onEvent(new ServerRespondsDeregister(data),socket);break;

                case eClient_Sends_Logout: node.onEvent(new ClientSendsLogout(data),socket);break;

                case eServer_Responds_Refresh: node.onEvent(new ServerRespondsRefresh(data),socket);break;

                default:

            }
        } catch(IOException e) {
            System.out.println("IO Exception in Event Factory");
            e.printStackTrace();
        }
    }



}

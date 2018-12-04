package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

public interface Protocol {

    //These values are unique identifiers for a message type

     String Server_Sends_Connect = "serverSendsConnect.msg";

     String Client_Make_Move = "clientMakeMove.msg";

     String Client_Request_Check_Move = "clientRequestCheckMove.msg";
     String Server_Responds_Check_Move = "serverRespondsCheckMove.msg";

     String Client_Request_Game_State = "clientRequestGameState.msg";
     String Server_Responds_Game_State = "serverRespondsGameState.msg";

     String Client_Sends_Registration = "clientSendsRegistration.msg";
     String Server_Responds_Registration = "serverRespondsRegistration.msg";

     String Client_Sends_Login = "clientSendsLogin.msg";
     String Server_Responds_Login = "serverRespondsLogin.msg";

     String Client_Sends_Refresh = "clientSendsRefresh.msg";
     String Server_Responds_Refresh = "serverRespondsRefresh.msg";

     String Client_Sends_Invite = "clientSendsInvite.msg";
     String Client_Responds_Invite = "clientRespondsInvite.msg";

     String Server_Sends_Invite = "serverSendsInvite.msg";
     String Server_Responds_Invite = "serverRespondsInvite.msg";

     String Client_Sends_Deregister = "clientSendsDeregister.msg";
     String Server_Responds_Deregister = "serverRespondsDeregister.msg";

     String Client_Sends_Logout = "clientSendsLogout.msg";

     int eClient_Make_Move = 1;
     int eClient_Request_Check_Move = 2;
     int eServer_Responds_Check_Move = 3;
     int eClient_Request_Game_State = 4;
     int eServer_Responds_Game_State = 5;
     int eClient_Sends_Registration = 6;
     int eServer_Responds_Registration = 7;
     int eClient_Sends_Login = 8;
     int eServer_Responds_Login = 9;
     int eClient_Sends_Refresh = 10;
     int eServer_Responds_Refresh = 11;
     int eClient_Sends_Invite = 12;
     int eClient_Responds_Invite = 13;
     int eServer_Sends_Invite = 14;
     int eServer_Responds_Invite = 15;
     int eClient_Sends_Deregister = 16;
     int eServer_Responds_Deregister = 17;
     int eClient_Sends_Logout = 18;

}

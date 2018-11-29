package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

public interface Protocol {

    //These values are unique identifiers for a message type

     String Server_Sends_Connect = "serverSendsConnect.msg";

     String Client_Make_Move = "clientMakeMove.msg";

     String Client_Request_Check_Move = "clientRequestCheckMove.msg";
     String Server_Responds_Check_Move = "serverRespondsCheckMove.msg";

     String Client_Request_Game_State = "clientRequestGameState.msg";
     String Server_Responds_Game_State = "serverRespondsGameState.msg";

     String Client_Sends_Register = "clientSendsRegister.msg";
     String Server_Responds_Register = "serverRespondsRegister.msg";

     String Client_Sends_Login = "clientSendsLogin.msg";
     String Server_Responds_Login = "serverRespondsLogin.msg";

     String Client_Sends_Get_History_Refresh = "clientSendsGetHistoryRefresh.msg";
     String Server_Responds_Get_History_Refresh = "serverRespondsGetHistoryRefresh.msg";

     String Client_Sends_Game_Invite_Refresh = "clientSendsGameInviteRefresh.msg";
     String Server_Responds_Game_Invite_Refresh = "clientSendsGameInviteRefresh.msg";

     String Client_Sends_Invite = "clientSendsInvite.msg";
     String Client_Responds_Invite = "clientRespondsInvite";

     String Server_Sends_Invite = "serverSendsInvite.msg";
     String Server_Responds_Invite = "serverRespondsInvite.msg";

}

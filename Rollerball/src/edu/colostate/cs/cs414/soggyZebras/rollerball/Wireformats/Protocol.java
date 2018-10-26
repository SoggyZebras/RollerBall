package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

public interface Protocol {

    //These values are unique identifiers for a message type

     String Client_Make_Move = "clientMakeMove.msg";

     String Client_Request_Game_State = "clientRequestGameState.msg";
     String Server_Responds_Game_State = "serverRespondsGameState.msg";

     String Client_Sends_Register = "clientSendsRegister.msg";
     String Server_Responds_Register = "serverRespondsRegister.msg";

     String Client_Sends_Login = "clientSendsLogin.msg";
     String Server_Responds_Login = "serverRespondsLogin.msg";

     String Client_Sends_Get_History = "clientSendsGetHistory.msg";
     String Server_Responds_Get_History = "serverRespondsGetHistory.msg";

     String Client_Sends_Game_Invite = "clientSendsGameInvite.msg";
     String Server_Responds_Game_Invite = "clientSendsGameInvite.msg";

}

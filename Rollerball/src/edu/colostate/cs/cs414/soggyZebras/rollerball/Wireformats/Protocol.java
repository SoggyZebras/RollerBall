package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

public interface Protocol {

    //These values are unique identifiers for a message type

    final byte Client_Make_Move = 0;
    final byte Server_Responds_Make_Move = 1;

    final byte Client_Request_Game_State = 2;
    final byte Server_Responds_Game_State = 3;

    final byte Client_Sends_Register = 4;
    final byte Server_Responds_Register = 5;

    final byte Client_Sends_Login = 6;
    final byte Server_Responds_Login = 7;

    final byte Client_Sends_Get_History = 8;
    final byte Server_Responds_Get_History = 9;

    final byte Client_Sends_Game_Invite = 10;
    final byte Server_Responds_Game_Invite = 11;

}

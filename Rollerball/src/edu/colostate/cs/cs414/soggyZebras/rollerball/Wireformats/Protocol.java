package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

public interface Protocol {

    //These values are unique identifiers for a message type

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

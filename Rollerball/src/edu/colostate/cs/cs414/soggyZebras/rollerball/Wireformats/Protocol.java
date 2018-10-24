package edu.colostate.cs.cs414.soggyZebras.rollerball.Wireformats;

public interface Protocol {

    //These values are unique identifiers for a message type

    final byte ClientMakeMove = 0;
    final byte ServerRespondsMakeMove = 1;

    final byte ClientRequestGameState = 2;
    final byte ServerRespondsGameState = 3;

    final byte ClientSendsRegister = 4;
    final byte ServerRespondsRegister = 5;

    final byte ClientSendsLogin = 6;
    final byte ServerRespondsLogin = 7;

    final byte ClientSendsGetHistory = 8;
    final byte ServerRespondsGetHistory = 9;

    final byte ClientSendsGameInvite = 10;
    final byte ServerRespondsGameInvite = 11;

}

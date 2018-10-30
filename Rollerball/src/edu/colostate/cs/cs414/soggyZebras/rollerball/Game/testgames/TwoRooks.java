package edu.colostate.cs.cs414.soggyZebras.rollerball.Game.testgames;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;

public class TwoRooks extends Game {

    public TwoRooks() {
        board.clear();

        addPiece(new Rook(new Location(0, 3), 'w', "rook"));
        addPiece(new Rook(new Location(2, 6), 'b', "rook"));
    }

}

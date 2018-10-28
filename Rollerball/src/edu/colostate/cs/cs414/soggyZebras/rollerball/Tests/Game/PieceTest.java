package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;

class PieceTest {

    Location eLocation = new Location(0,0);
    Location iLocation = new Location(1, 1);
    Piece externalPiece = new Piece(eLocation, 'b', "pawn");
    Piece internalPiece = new Piece(iLocation, 'b', "pawn");

    @Test
    void testConstructorRow() {
        Piece p = new Piece(eLocation, 'b', "pawn");
        assertEquals(p.getRow(), eLocation.getRow());
    }

    @Test
    void testConstructorCol() {
        Piece p = new Piece(eLocation, 'b', "king");
        assertEquals(p.getCol(), eLocation.getCol());
    }

    @Test
    void testConstructorColor() {
        Piece p = new Piece(eLocation, 'b', "king");
        assertEquals(p.getColor(), 'b');
    }

    @Test
    void testConstructorType() {
        Piece p = new Piece(eLocation, 'b', "king");
        assertEquals(p.getType(), "king");
    }

    @Test
    void testExternalRowOne() {
        assertTrue(externalPiece.ExternalRing());
    }

    @Test
    void testExternalRowSeven() {
        assertTrue(externalPiece.ExternalRing());
    }

    @Test
    void testExternalColOne() {
        assertTrue(externalPiece.ExternalRing());
    }

    @Test
    void testExternalColSeven() {
        assertTrue(externalPiece.ExternalRing());
    }

    @Test
    void testInternalRing() {
        assertFalse(internalPiece.ExternalRing());
    }

}

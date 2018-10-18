package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;

class PieceTest {

    Location eLocation = new Location(1,1);
    Location iLocation = new Location(2, 2);
    Piece externalPiece = new Piece(eLocation, 'b');
    Piece internalPiece = new Piece(iLocation, 'b');

    @Test
    void testConstructorRow() {
        Piece p = new Piece(eLocation, 'b');
        assertEquals(p.getRow(), eLocation.getRow());
    }

    @Test
    void testConstructorCol() {
        Piece p = new Piece(eLocation, 'b');
        assertEquals(p.getCol(), eLocation.getCol());
    }

    @Test
    void testConstructorColor() {
        Piece p = new Piece(eLocation, 'b');
        assertEquals(p.getColor(), 'b');
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

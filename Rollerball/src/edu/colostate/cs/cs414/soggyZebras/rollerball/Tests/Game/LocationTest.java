package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

import java.util.Locale;

class LocationTest {

    @Test
    void testRowConstructor() {
        Location l = new Location(1,2);
        assertEquals(l.getRow(), 1);
    }

    @Test
    void testColConstructor() {
        Location l = new Location(1,2);
        assertEquals(l.getCol(), 2);
    }

    @Test
    void testEqualsT() {
        assertEquals(new Location(1, 1), new Location(1, 1));
    }

    @Test
    void testEqualsF() {
        assertNotEquals(new Location(1, 1), new Location(1, 2));
    }

    @Test
    void testToString() {
        assertEquals("1 1", new Location(1, 1).toString());
    }

    @Test
    void testHashCode() {
        assertEquals("1 1".hashCode(), new Location(1, 1).hashCode());
    }
}
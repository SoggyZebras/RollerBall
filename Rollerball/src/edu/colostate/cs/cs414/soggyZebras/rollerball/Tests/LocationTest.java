package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

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

}
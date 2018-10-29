package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.King;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

class KingTest {

	Location l = new Location(0, 0);
	King k = new King(l, 'b', "king");

	@Test
	void test() {
		assertEquals(true, true);
	}

}

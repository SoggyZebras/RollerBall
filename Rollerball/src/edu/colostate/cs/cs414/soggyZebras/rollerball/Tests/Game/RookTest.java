package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RookTest {
	Rook topOuter, topInner, leftOuter, leftInner,
			bottomOuter, bottomInner, rightOuter, rightInner;

	Location up, down, left, right;

	@BeforeEach
	public void init() {
		topOuter = new Rook(new Location(0, 0), 'w', "rook");
		topInner = new Rook(new Location(1, 1), 'w', "rook");
		leftOuter = new Rook(new Location(6, 0), 'w', "rook");
		leftInner = new Rook(new Location(5, 1), 'w', "rook");
		bottomOuter = new Rook(new Location(6, 6), 'w', "rook");
		bottomInner = new Rook(new Location(5, 5), 'w', "rook");
		rightOuter = new Rook(new Location(0, 6), 'w', "rook");
		rightInner = new Rook(new Location(1, 5), 'w', "rook");

		up = new Location(-1, 0);
		down = new Location(1, 0);
		left = new Location(0, -1);
		right = new Location(0, 1);
	}

	@Test
	void testGetForwardTop() {
		assertEquals(right, topOuter.getForward());
		assertEquals(right, topInner.getForward());
	}

	@Test
	void testGetForwardLeft() {
		assertEquals(up, leftInner.getForward());
		assertEquals(up, leftOuter.getForward());
	}

	@Test
	void testGetForwardRight() {
		assertEquals(down, rightOuter.getForward());
		assertEquals(down, rightInner.getForward());
	}

	@Test
	void testGetForwardBottom() {
		assertEquals(left, bottomOuter.getForward());
		assertEquals(left, bottomInner.getForward());
	}
}

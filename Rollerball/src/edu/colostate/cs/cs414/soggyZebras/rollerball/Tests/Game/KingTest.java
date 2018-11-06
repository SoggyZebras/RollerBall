package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.King;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

class KingTest {

	private Game newGame = new Game();

	private King colZero, startPos, pieceToRight, downLeft, colSix, enemyInPath, noMoves;

	@BeforeEach
	public void init() {
		colZero = new King(new Location(0,0), 'w', "king");
		startPos = new King(new Location(1,3), 'b', "king");
		pieceToRight = new King(new Location(0, 1), 'b', "king");
		downLeft = new King(new Location(2, 1), 'b', "king");
		colSix = new King(new Location(3, 6), 'b', "king");
		enemyInPath = new King(new Location(0, 1), 'w', "king");
		noMoves = new King(new Location(1,3), 'b', "king");
	}

	@Test
	void colZeroCorner() {
		ArrayList<Location> a = colZero.validMoves(newGame.getBoard());
		assertEquals(3, a.size());
		assertTrue(a.contains(new Location(0, 1)));
		assertTrue(a.contains(new Location(1,1)));
		assertTrue(a.contains(new Location(1,0)));
	}

	@Test
	void startPosition() {
		ArrayList<Location> a = startPos.validMoves(newGame.getBoard());
		assertEquals(0, a.size());
	}

	@Test
	void testPieceToRight() {
		ArrayList<Location> a = pieceToRight.validMoves(newGame.getBoard());
		assertEquals(3, a.size());
		assertTrue(a.contains(new Location(0,0)));
		assertTrue(a.contains(new Location(1,0)));
		assertTrue(a.contains(new Location(1,1)));
	}

	@Test
	void testDownLeft() {
		ArrayList<Location> a = downLeft.validMoves(newGame.getBoard());
		assertEquals(5, a.size());
		assertTrue(a.contains(new Location(3,0)));
	}

	@Test
	void testColSix() {
		ArrayList<Location> a = colSix.validMoves(newGame.getBoard());
		assertEquals(5, a.size());

		colSix = new King(new Location(0, 6), 'b', "king");
		a = colSix.validMoves(newGame.getBoard());
		assertEquals(3, a.size());
	}

	@Test
	void canCapture() {
		ArrayList<Location> a = enemyInPath.validMoves(newGame.getBoard());
		assertEquals(5, a.size());
		assertTrue(a.contains(new Location(0, 2)));
		assertTrue(a.contains(new Location(1,2)));
	}

	@Test
	void noMoves() {
		ArrayList<Location> a = noMoves.validMoves(newGame.getBoard());
		assertEquals(0, a.size());
	}
}

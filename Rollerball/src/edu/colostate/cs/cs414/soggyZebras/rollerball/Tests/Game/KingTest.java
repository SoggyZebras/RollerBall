package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.King;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;

class KingTest {

	HashMap board = new HashMap<>();

	private User one = new User(1, "test", "test", "test");
	private User two = new User(2, "test", "test", "test");
	private Game newGame = new Game(1, one, two);

	private King colZero, startPos, pieceToRight, downLeft, colSix, enemyInPath, noMoves;

	@BeforeEach
	public void init() {
		colZero = new King(new Location(0,0), 'w', "king");
		startPos = new King(new Location(1,3), 'b', "king");
		pieceToRight = new King(new Location(0, 1), 'b', "king");
		downLeft = new King(new Location(2, 1), 'b', "king");
		colSix = new King(new Location(3, 6), 'b', "king");
		enemyInPath = new King(new Location(0, 1), 'w', "king");
		noMoves = new King(new Location(1,3), 'w', "king");
	}

	@Test
	void colZeroCorner() {
		ArrayList<Location> a = colZero.validMoves(board);
		assertEquals(3, a.size());
		assertTrue(a.contains(new Location(0, 1)));
		assertTrue(a.contains(new Location(1,1)));
		assertTrue(a.contains(new Location(1,0)));
	}

	@Test
	void startPosition() {
		ArrayList<Location> a = startPos.validMoves(board);
		assertEquals(5, a.size());
	}

	@Test
	void testColSix() {
		ArrayList<Location> a = colSix.validMoves(board);
		assertEquals(5, a.size());
		colSix = new King(new Location(0, 6), 'b', "king");
		a = colSix.validMoves(board);
		assertEquals(3, a.size());
	}

	@Test
	void canCapture() {
		ArrayList<Location> a = enemyInPath.validMoves(board);
		assertEquals(5, a.size());
		assertTrue(a.contains(new Location(0, 2)));
		assertTrue(a.contains(new Location(1,2)));
	}

	@Test
	void noMoves() {
		//Starting black king is at [1,3].
		ArrayList<Location> a = newGame.validMoves(new Location(1,3));
		assertEquals(0, a.size());
	}
}

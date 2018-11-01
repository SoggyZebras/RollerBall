package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Game;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class RookTest {
	private Rook topOuter, topInner, leftOuter, leftInner,
			bottomOuter, bottomInner, rightOuter, rightOuterB, rightInner;

	private Location up, down, left, right;

	private Game newGame;

	private HashMap<Location,Piece> board1;

	@BeforeEach
	public void init() {
		topOuter = new Rook(new Location(0, 0), 'w', "rook");
		topInner = new Rook(new Location(1, 1), 'w', "rook");
		leftOuter = new Rook(new Location(6, 0), 'w', "rook");
		leftInner = new Rook(new Location(5, 1), 'w', "rook");
		bottomOuter = new Rook(new Location(6, 6), 'w', "rook");
		bottomInner = new Rook(new Location(5, 5), 'w', "rook");
		rightOuter = new Rook(new Location(0, 6), 'w', "rook");
		rightOuterB = new Rook(new Location(0, 6), 'b', "rook");
		rightInner = new Rook(new Location(1, 5), 'w', "rook");

		up = new Location(-1, 0);
		down = new Location(1, 0);
		left = new Location(0, -1);
		right = new Location(0, 1);

		newGame = new Game();

		board1 = new HashMap<>();
	}

	@Test
	void testFriendlyPieceInTheWay() {
		// checks the case where the pieces movement is stopped by a friendly piece being in its path
		board1.put(topOuter.getLoc(), topOuter);
		board1.put(rightOuter.getLoc(), rightOuter);
		ArrayList<Location> validMoves = topOuter.validMoves(board1);
		assertFalse(validMoves.contains(rightOuter.getLoc()));
	}

	@Test
	void testEnemyPieceStopsMovement() {
		// checks the case where the pieces movement is stopped because they captured an enemy piece
		board1.put(topOuter.getLoc(), topOuter);
		board1.put(rightOuterB.getLoc(), rightOuterB);
		ArrayList<Location> validMoves = topOuter.validMoves(board1);
		assertTrue(validMoves.contains(rightOuterB.getLoc()));
		assertFalse(validMoves.contains(new Location(1, 6)));
	}

	@Test
	void testValidMovesLHSOuter() {
		// check the valid moves for a piece on the left side of the board in the outer ring
		board1.put(leftOuter.getLoc(), leftOuter);
		ArrayList<Location> validMoves = leftOuter.validMoves(board1);
		assertEquals(13, validMoves.size());
		assertTrue(validMoves.contains(new Location(0, 0)));
		assertTrue(validMoves.contains(new Location(5, 0)));
		assertTrue(validMoves.contains(new Location(0, 6)));
		assertTrue(validMoves.contains(new Location(6, 1)));
	}

	@Test
	void testValidMovesLHSInner() {
		// check the valid moves for a piece on the left side of the board in the inner ring
		board1.put(leftInner.getLoc(), leftInner);
		ArrayList<Location> validMoves = leftInner.validMoves(board1);
		assertEquals(8, validMoves.size());
		assertTrue(validMoves.contains(new Location(0, 1)));
		assertTrue(validMoves.contains(new Location(5, 0)));
		assertTrue(validMoves.contains(new Location(5, 2)));
		assertTrue(validMoves.contains(new Location(6, 1)));
	}

	// TODO: add more tests for other quadrants of board

	@Test
	void testNewGameValidMoves() {
		Map<Location, Piece> board = newGame.getBoard();

		// test rooks at top
		Rook rook1 = (Rook) board.get(new Location(0, 2));
		Rook rook2 = (Rook) board.get(new Location(0, 2));
		ArrayList<Location> valids = rook1.validMoves(board);
		assertTrue(valids.contains(new Location(rook1.getRow() + left.row, rook1.getCol() + left.col)));
		assertEquals(1, valids.size());
		valids = rook2.validMoves(board);
		assertTrue(valids.contains(new Location(rook2.getRow() + left.row, rook2.getCol() + left.col)));
		assertEquals(1, valids.size());

		// test rooks at bottom
		Rook rook3 = (Rook) board.get(new Location(5, 4));
		Rook rook4 = (Rook) board.get(new Location(6, 4));
		valids = rook3.validMoves(board);
		assertTrue(valids.contains(new Location(rook3.getRow() + right.row, rook3.getCol() + right.col)));
		assertEquals(1, valids.size());
		valids = rook4.validMoves(board);
		assertTrue(valids.contains(new Location(rook4.getRow() + right.row, rook4.getCol() + right.col)));
		assertEquals(1, valids.size());


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

	@Test
	void testGetLeftTop() {
		assertEquals(up, topOuter.getLeft());
		assertEquals(up, topInner.getLeft());
	}

	@Test
	void testGetLeftLeft() {
		assertEquals(left, leftInner.getLeft());
		assertEquals(left, leftOuter.getLeft());
	}

	@Test
	void testGetLeftRight() {
		assertEquals(right, rightOuter.getLeft());
		assertEquals(right, rightInner.getLeft());
	}

	@Test
	void testGetLeftBottom() {
		assertEquals(down, bottomOuter.getLeft());
		assertEquals(down, bottomInner.getLeft());
	}

	@Test
	void testGetRightTop() {
		assertEquals(down, topOuter.getRight());
		assertEquals(down, topInner.getRight());
	}

	@Test
	void testGetRightLeft() {
		assertEquals(right, leftInner.getRight());
		assertEquals(right, leftOuter.getRight());
	}

	@Test
	void testGetRightRight() {
		assertEquals(left, rightOuter.getRight());
		assertEquals(left, rightInner.getRight());
	}

	@Test
	void testGetRightBottom() {
		assertEquals(up, bottomOuter.getRight());
		assertEquals(up, bottomInner.getRight());
	}

	@Test
	void testGetBackwardTop() {
		assertEquals(left, topOuter.getBackward());
		assertEquals(left, topInner.getBackward());
	}

	@Test
	void testGetBackwardLeft() {
		assertEquals(down, leftInner.getBackward());
		assertEquals(down, leftOuter.getBackward());
	}

	@Test
	void testGetBackwardRight() {
		assertEquals(up, rightOuter.getBackward());
		assertEquals(up, rightInner.getBackward());
	}

	@Test
	void testGetBackwardBottom() {
		assertEquals(right, bottomOuter.getBackward());
		assertEquals(right, bottomInner.getBackward());
	}
}

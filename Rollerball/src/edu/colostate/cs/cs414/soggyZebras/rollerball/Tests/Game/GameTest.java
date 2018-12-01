package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class GameTest {

	@Test
	void testBlackWin() {


		Map<Location, Piece> testMap = new HashMap<>();
		Game testGame = new Game(testMap);

		testGame.addPiece(new Pawn(new Location(5, 2), 'w', "pawn"));
		testGame.addPiece(new Pawn(new Location(6, 2), 'w', "pawn"));
		testGame.addPiece(new King(new Location(5, 3), 'w', "king"));
		testGame.addPiece(new Bishop(new Location(6, 3), 'w', "bishop"));
		testGame.addPiece(new Pawn(new Location(5, 4), 'b', "pawn")); //this is added to check win condition
		testGame.addPiece(new Rook(new Location(6, 4), 'w', "rook"));

		assertTrue(testGame.wonGameB());

	}

	@Test
	void testWhiteWin() {


		Map<Location, Piece> testMap = new HashMap<>();
		Game testGame = new Game(testMap);

		testGame.addPiece(new Pawn(new Location(0, 4), 'b', "pawn"));
		testGame.addPiece(new Pawn(new Location(1, 4), 'b', "pawn"));
		testGame.addPiece(new King(new Location(1, 3), 'b', "king"));
		testGame.addPiece(new Bishop(new Location(0, 3), 'b', "bishop"));
		testGame.addPiece(new Rook(new Location(0, 2), 'b', "rook"));
		testGame.addPiece(new Pawn(new Location(1, 2), 'w', "pawn"));

		assertTrue(testGame.wonGameW());

	}

	@Test
	void notWon() {
			Game testGame = new Game();
			assertFalse(testGame.wonGameB());
			assertFalse(testGame.wonGameW());
	}




}

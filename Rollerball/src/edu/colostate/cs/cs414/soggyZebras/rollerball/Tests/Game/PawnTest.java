package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Pawn;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

class PawnTest {

	//Quad 1 is BOTTOM - moves clockwise around board
	//Quad 2 is LEFT
	//Quad 3 is TOP
	//Quad 4 is RIGHT

	HashMap<Location,Piece> board1 = new HashMap<>(); //Initially empty

	Location LocTOP = new Location(0,0);
	Location LocBOTTOM = new Location(5,3);
	Location LocLEFT = new Location(3,0);
	Location LocRIGHT = new Location(4,5);

	Pawn PTestTOP = new Pawn(LocTOP, 'w', "pawn");
	Pawn PTestBOTTOM = new Pawn (LocBOTTOM, 'w',"pawn");
	Pawn PTestLEFT = new Pawn (LocLEFT, 'w',"pawn");
	Pawn PTestRIGHT = new Pawn (LocRIGHT, 'w',"pawn");

	@Test
	void testQuads() {

		assertTrue(PTestTOP.getQuadrant()==3); //top quad
		assertTrue(PTestBOTTOM.getQuadrant()==1); //bottom quad
		assertTrue(PTestLEFT.getQuadrant()==2);
		assertTrue(PTestRIGHT.getQuadrant()==4);

		assertFalse(PTestBOTTOM.ExternalRing());
		assertTrue(PTestTOP.ExternalRing());

	}

	@Test
	void testMoveBlankBoard(){

		//Test for correct moves for PTestTOP (moving right):
		Location v1 = new Location(0,1);
		Location v2 = new Location(1,1);
		ArrayList<Location> compare = PTestTOP.validMoves(board1);
		assertTrue(compare.contains(v1));
		assertTrue(compare.contains(v2));

		//Test for correct moves for PTestRIGHT (moving down):
		//Placed in location where pawn condition of 3 moves is possible
		v1 = new Location(5,4);
		v2 = new Location (5,5);
		Location v3 = new Location(5,6);
		compare.clear();
		compare = PTestRIGHT.validMoves(board1);
		assertTrue(compare.contains(v1));
		assertTrue(compare.contains(v2));
		assertTrue(compare.contains(v3));

		//Test for correct moves for PTestLEFT (moving up):
		v1 = new Location(2,0);
		v2 = new Location (2,1);
		compare.clear();
		compare = PTestLEFT.validMoves(board1);
		assertTrue(compare.contains(v1));
		assertTrue(compare.contains(v2));

		//Test for correct moves for PTestBOTTOM (moving left):
		v1 = new Location(5,2);
		v2 = new Location (6,2);
		compare.clear();
		compare = PTestBOTTOM.validMoves(board1);
		assertTrue(compare.contains(v1));
		assertTrue(compare.contains(v2));

	}


	@Test
	void testFriendlyMove(){
		Location v1 = new Location(0,1);
		Location v2 = new Location(1,1);

		//Test for correct moves for PTestTOP (1 friendly piece at 1,1):
		LocTOP = new Location(1,1);
		Piece testFriendly1 = new Piece(LocTOP, 'w',"rook");
		board1.put(LocTOP,testFriendly1);

		ArrayList<Location> compare = PTestTOP.validMoves(board1);
		assertTrue(compare.contains(v1));
		assertFalse(compare.contains(v2));
		assertTrue(compare.size()==1);
	}

	@Test
	void testEnemyMove(){
		Location v1 = new Location(0,1);
		Location v2 = new Location(1,1);

		//Test for correct moves for PTestTOP (1 enemy piece at 1,1):
		LocTOP = new Location(1,1);
		Piece testFriendly1 = new Piece(LocTOP, 'b',"bishop");
		board1.put(LocTOP,testFriendly1);

		ArrayList<Location> compare = PTestTOP.validMoves(board1);
		assertTrue(compare.contains(v1));
		assertTrue(compare.contains(v2));
		assertTrue(compare.size()==2);
	}


}

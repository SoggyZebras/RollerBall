package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Game;

import static org.junit.jupiter.api.Assertions.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Bishop;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Piece;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class BishopTest {
	HashMap board = new HashMap<>();

	@Test
	void testQuadOne_1() {
		Bishop b = new Bishop(new Location(6,3), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(5,2));
		temp.add(new Location(4,1));
		temp.add(new Location(3,0));
		temp.add(new Location(2,1));
		temp.add(new Location(1,2));
		temp.add(new Location(0,3));
		temp.add(new Location(5,4));
		assertEquals(temp, b.validMoves(board));
	}

	@Test
	void testQuadOne_2() {
		Bishop b = new Bishop(new Location(5,2), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(4,1));
		temp.add(new Location(3,0));
		temp.add(new Location(2,1));
		temp.add(new Location(1,2));
		temp.add(new Location(0,3));
		temp.add(new Location(6,3));
		temp.add(new Location(6,1));
		temp.add(new Location(5,0));
		assertEquals(temp, b.validMoves(board));
	}

	@Test
	void testQuadOne_3() {
		Bishop b = new Bishop(new Location(6,1), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(5,0));
		temp.add(new Location(5,2));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}

	@Test
	void testQuadOne_4() {
		Bishop b = new Bishop(new Location(5,0), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(6,1));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}


	@Test
	void testQuadOne_5() {
		Bishop b = new Bishop(new Location(5,0), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(6,1));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}


	@Test
	void testQuadOneInvalid_1() {
		Bishop b = new Bishop(new Location(6,3), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(5,2));
		temp.add(new Location(4,1));
		temp.add(new Location(3,0));
		temp.add(new Location(2,1));
		temp.add(new Location(1,2));
		temp.add(new Location(0,3));
		temp.add(new Location(5,4));
		assertEquals(temp, b.validMoves(board));
	}

	@Test
	void testQuadOneInvalid_2() {
		Bishop b = new Bishop(new Location(5,2), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(4,1));
		temp.add(new Location(3,0));
		temp.add(new Location(2,1));
		temp.add(new Location(1,2));
		temp.add(new Location(0,3));
		temp.add(new Location(6,3));
		temp.add(new Location(6,1));
		temp.add(new Location(5,0));
		assertEquals(temp, b.validMoves(board));
	}

	@Test
	void testQuadOneInvalid_3() {
		Bishop b = new Bishop(new Location(6,1), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(5,0));
		temp.add(new Location(5,2));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}

	@Test
	void testQuadOneInvalid_4() {
		Bishop b = new Bishop(new Location(5,0), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(6,1));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}


	@Test
	void testQuadOneInvalid_5() {
		Bishop b = new Bishop(new Location(5,0), 'w', "");
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(6,1));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}





}

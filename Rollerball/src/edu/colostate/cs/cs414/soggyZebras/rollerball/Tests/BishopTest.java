package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests;

import static org.junit.jupiter.api.Assertions.*;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Bishop;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;

class BishopTest {
	HashMap board = new HashMap <>();

	@BeforeEach
	void init() {
		board.put(new Location(0, 0), null);
		board.put(new Location(0, 1), null);
		board.put(new Location(0, 2), null);
		board.put(new Location(0, 3), null);
		board.put(new Location(0, 4), null);
		board.put(new Location(0, 5), null);
		board.put(new Location(0, 6), null);
		board.put(new Location(1, 0), null);
		board.put(new Location(1, 1), null);
		board.put(new Location(1, 2), null);
		board.put(new Location(1, 3), null);
		board.put(new Location(1, 4), null);
		board.put(new Location(1, 5), null);
		board.put(new Location(1, 6), null);
		board.put(new Location(2, 0), null);
		board.put(new Location(2, 1), null);
		board.put(new Location(2, 5), null);
		board.put(new Location(2, 6), null);
		board.put(new Location(3, 0), null);
		board.put(new Location(3, 1), null);
		board.put(new Location(3, 5), null);
		board.put(new Location(3, 6), null);
		board.put(new Location(4, 0), null);
		board.put(new Location(4, 1), null);
		board.put(new Location(4, 5), null);
		board.put(new Location(4, 6), null);
		board.put(new Location(5, 0), null);
		board.put(new Location(5, 1), null);
		board.put(new Location(5, 2), null);
		board.put(new Location(5, 3), null);
		board.put(new Location(5, 4), null);
		board.put(new Location(5, 5), null);
		board.put(new Location(5, 6), null);
		board.put(new Location(6, 0), null);
		board.put(new Location(6, 1), null);
		board.put(new Location(6, 2), null);
		board.put(new Location(6, 3), null);
		board.put(new Location(6, 4), null);
		board.put(new Location(6, 5), null);
		board.put(new Location(6, 6), null);
	}

	@Test
	void testQuadOne_1() {
		Bishop b = new Bishop(new Location(6,3), 'w');
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
		Bishop b = new Bishop(new Location(5,2), 'w');
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
		Bishop b = new Bishop(new Location(6,1), 'w');
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(5,0));
		temp.add(new Location(5,2));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}

	@Test
	void testQuadOne_4() {
		Bishop b = new Bishop(new Location(5,0), 'w');
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(6,1));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}


	@Test
	void testQuadOne_5() {
		Bishop b = new Bishop(new Location(5,0), 'w');
		System.out.println(b.validMoves(board));
		ArrayList temp = new ArrayList<Location>();
		temp.add(new Location(6,1));
		temp.add(new Location(4,1));
		assertEquals(temp, b.validMoves(board));
	}




}

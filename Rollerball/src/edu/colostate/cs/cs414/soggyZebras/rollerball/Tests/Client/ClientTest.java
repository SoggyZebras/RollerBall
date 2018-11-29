package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Client;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Game.Location;
import edu.colostate.cs.cs414.soggyZebras.rollerball.Server.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ClientTest {

	/**
	 *
	 * @throws IOException
	 */
	@Test
	void testConstructorNullArg() throws IOException {

		//Throw exception if the Client object was created wit null a argument(s)
		assertThrows(IOException.class, () -> {new Client(null,5000);});
	}

	/**
	 *
	 * @throws IOException
	 */
	@Test
	void testMakeMove() throws IOException {

		//Thow an exception if the selector was not created correctly
		Thread t1 = new Thread(new Server(5000));
		t1.start();
		Client c = new Client("127.0.0.1", 5000);
		c.initialize();

		assertTrue(c.makeMove(new Location(0,0),new Location(0,1)));

	}

	/**
	 *
	 * @throws IOException
	 */
	@Test
	void testCheckValidMove() throws IOException {

		//Thow an exception if the selector was not created correctly
		Thread t1 = new Thread(new Server(5000));
		t1.start();
		Client c = new Client("127.0.0.1", 5000);
		c.initialize();

		assertTrue(c.checkValidMove(new Location(0,1)));

	}

	@Test
	void testgetGameState() throws IOException {

		//Thow an exception if the selector was not created correctly
		Thread t1 = new Thread(new Server(5000));
		t1.start();
		Client c = new Client("127.0.0.1", 5000);
		c.initialize();

		assertTrue(c.getGameState());

	}

}

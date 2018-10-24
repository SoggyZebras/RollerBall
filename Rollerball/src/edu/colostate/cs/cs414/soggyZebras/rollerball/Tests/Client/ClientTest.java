package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Client;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ClientTest {


	@Test
	void testConstructorNullArg() throws IOException {

		//Throw exception if the Client object was created wit null a argument(s)
		assertThrows(IOException.class, () -> {new Client(null,5000);});
	}

	@Test
	void testCreateClientSelector() throws IOException {

		//Thow an exception if the selector was not created correctly
		Client c = new Client("127.0.0.1", 5000);
	}

}

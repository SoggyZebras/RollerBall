package edu.colostate.cs.cs414.soggyZebras.rollerball.Tests.Client;

import static org.junit.jupiter.api.Assertions.*;

import edu.colostate.cs.cs414.soggyZebras.rollerball.Client.Client;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ClientTest {

	@Test
	void testConstructorNoArg() {

		//Throw an exception if the Client class was created with no arguments
		assertThrows(IOException.class, () -> {new Client();});
	}

	@Test
	void testConstructorNullArg() throws IOException {

		//Throw exception if the Client object was created wit null a argument(s)
		Client c = new Client();
	}

}

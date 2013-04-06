package de.jotschi.acoustid.client;
import java.io.IOException;

import org.junit.Test;

public class BasicConnectionTest {

	@Test
	public void testBasicConnection() throws IOException {

		FPIServerConnection connection = new FPIServerConnection("YOURSERVER", 6080);
		connection.connect();
		
		String response = connection.sendCommand("begin");
		System.out.println(response);
		connection.close();

	}
}

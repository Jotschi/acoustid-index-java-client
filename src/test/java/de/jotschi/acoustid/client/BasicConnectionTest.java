package de.jotschi.acoustid.client;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;


public class BasicConnectionTest {
	
	private static Logger log = Logger.getLogger(BasicConnectionTest.class);

	@Test
	public void testBasicConnection() throws IOException {

		FPIServerConnection connection = new FPIServerConnection("YOURSERVER", 6080);
		connection.connect();
		
		String response = connection.sendCommand("begin");
		log.debug(response);
		connection.close();

	}
}

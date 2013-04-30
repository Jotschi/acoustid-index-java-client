package de.jotschi.acoustid.client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

public class BasicConnectionTest {

	private static Logger log = Logger.getLogger(BasicConnectionTest.class);

	@Test
	public void testBasicConnection() throws IOException {

		FPIServerConnection conn = new FPIServerConnection("YOURHOST", 6080);
		conn.connect();

		String response = "";
		response = conn.sendCommand(Command.BEGIN);
		response = conn.insertFingerprint(1, new Fingerprint("368308215,364034037,397576085,397509509,393249669,389054869"));
		response = conn.insertFingerprint(2, new Fingerprint("1574172159,1598222797,1564660173,1564656069,1564537317,1565584741"));
		response = conn.insertFingerprint(3, new Fingerprint("1130316157,1096749341,1075786015,1075655999,1075656047,1079977343"));
		response = conn.sendCommand(Command.COMMIT);
		log.debug(response);
		Map<Long, Long> result = conn.search(new Fingerprint("1130316157,397509509,393249669,389054869"));
		assertTrue(result.get(1) == 3);
		log.debug(result);

		log.debug(response);
		response = conn.sendCommand(Command.QUIT);
		log.debug(response);

		conn.close();

	}
}

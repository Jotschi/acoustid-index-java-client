package de.jotschi.acoustid.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * The Class FPIServerConnection.
 * 
 * <p>
 * Provides a connection class to open connections to an indexer server.
 */
public class FPIServerConnection {

	private static Logger log = Logger.getLogger(FPIServerConnection.class);

	/** The port of the fingerprint server */
	private int port = 6080;

	/** The hostname of the fingerprint server */
	private String hostname = "localhost";

	/** The Socket t, it will be used to open the connection */
	private Socket t;

	private PrintStream out;

	private BufferedReader in;

	/**
	 * Creates a fpi server connection.
	 */
	public FPIServerConnection() {
		log.debug("Initializing new server connection");
	}

	/**
	 * Creates a fpi server connection with the given parameters.
	 * 
	 * @param hostname
	 *            the hostname
	 * @param port
	 *            the port
	 */
	public FPIServerConnection(String hostname, int port) {
		log.debug("Initializing new server connection with this config: " + this.hostname + " with port " + this.port);

		this.hostname = hostname;
		this.port = port;
	}

	public String sendCommand(Command command) throws IOException {
		out.print(command + "\r\n");
		out.flush();
		return in.readLine();
	}

	/**
	 * Send command.
	 * 
	 * <p>
	 * 
	 * @param command
	 *            the command
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String sendCommand(String command) throws IOException {
		out.print(command + "\r\n");
		out.flush();
		return in.readLine();
	}

	/**
	 * Connect.
	 * 
	 * <p>
	 * opens a new socket connection to the configured hostname and the configured port
	 * 
	 * @return true, if successful
	 */
	public boolean connect() {
		log.debug("opening socket connection to " + this.hostname + " with port " + this.port);

		try {

			t = new Socket(this.hostname, this.port);
			t.setKeepAlive(true);
			out = new PrintStream(t.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(t.getInputStream()));

		} catch (UnknownHostException e) {
			log.error("Unknown Host Exception. Please ensure host settings", e);
			return false;
		} catch (IOException e) {
			log.error("Error while getting socket output", e);
			return false;
		}
		return true;
	}

	/**
	 * Closes an existing socket connection, if there is one left
	 */
	public void close() {
		if (t != null)
			try {
				t.close();
			} catch (IOException e) {
				log.error("Error while closing socket!", e);
			}
	}

	/**
	 * Inserts the given fingerprint at position i
	 * 
	 * @param i
	 * @param fingerprint
	 * @return
	 * @throws IOException
	 */
	public String insertFingerprint(int i, Fingerprint fingerprint) throws IOException {
		return this.sendCommand(Command.INSERT + " " + i + " " + fingerprint);
	}

	/**
	 * Invokes a search using the given fingerprint
	 * 
	 * @param fingerprint
	 * @return
	 * @throws IOException
	 */
	public Map<Integer, Integer> search(Fingerprint fingerprint) throws IOException {
		String response = this.sendCommand(Command.SEARCH + " " + fingerprint);
		if (StringUtils.isEmpty(response) || !response.startsWith("OK")) {
			throw new IOException("The search request failed with response {" + response + "}");
		}
		Map<Integer, Integer> list = new HashMap<Integer, Integer>();
		String[] parts = response.split(" ");
		boolean skipFirst = true;
		for (String str : parts) {
			if (skipFirst) {
				skipFirst = false;
				continue;
			}
			String[] entry = str.split(":");
			list.put(Integer.parseInt(entry[0]), Integer.parseInt(entry[1]));
		}

		return list;
	}
}

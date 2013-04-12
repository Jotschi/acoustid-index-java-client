package de.jotschi.acoustid.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * The Class FPIServerConnection.
 * 
 * <p>
 * Provides a connection class to open connections to an indexer server.
 */
public class FPIServerConnection {
	
	/** The logger. */
	private static Logger log = Logger.getLogger(FPIServerConnection.class);

	/** The port. */
	private int port = 6080;
	
	/** The hostname. */
	private String hostname = "localhost";
	
	/** The Socket t, it will be used to open the connection */
	private Socket t;
	
	/** The SprintStream out. */
	private PrintStream out;
	
	/** The BufferedReader in. */
	private BufferedReader in;
	
	
	/**
	 * Creates a fpi server connection.
	 */
	public FPIServerConnection () {
		log.debug("Initializing new server connection");
	}

	/**
	 * Creates a fpi server connection with the given parameters.
	 *
	 * @param hostname the hostname
	 * @param port the port
	 */
	public FPIServerConnection(String hostname, int port) {
		log.debug("Initializing new server connection with this config: " + this.hostname + " with port " + this.port);
		
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * Send command.
	 * 
	 * <p>
	 *
	 * @param command the command
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
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
}

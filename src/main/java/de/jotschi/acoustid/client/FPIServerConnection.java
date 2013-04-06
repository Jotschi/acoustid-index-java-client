package de.jotschi.acoustid.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class FPIServerConnection {

	private int port = 6080;
	private String hostname;
	private Socket t;
	PrintStream out;
	BufferedReader in;

	/**
	 * Creates a fpi server connection
	 * 
	 * @param hostname
	 * @param port
	 */
	public FPIServerConnection(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public String sendCommand(String command) throws IOException {
		out.print(command + "\r\n");
		out.flush();
		return in.readLine();
	}

	public boolean connect() {
		try {
			t = new Socket(this.hostname, this.port);
			t.setKeepAlive(true);

			out = new PrintStream(t.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(t.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + hostname);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public void close() {
		if (t != null)
			try {
				t.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}

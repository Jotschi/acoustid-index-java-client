package de.jotschi.acoustid.client;

public enum Commands {

	INSERT("insert"), ECHO("echo"), COMMIT("commit"), BEGIN("begin"), CLEANUP("cleanup"), SEARCH("search"), OPTIMIZE("optimze"), ROLLBACK("rollback"), GET("get"), SET("set"), KILL(
			"kill"), QUIT("quit");
	private Commands(final String text) {
		this.text = text;
	}

	private final String text;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}

}

package eu.eudat.b2access.balana;

public class AuthorisationException extends Exception {
	public AuthorisationException(String msg) {
		super(msg);
	}
	
	public AuthorisationException(String msg, Throwable t) {
		super(msg,t);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

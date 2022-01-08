package br.com.furafila.credentialsapp.exception;

public class CredentialsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2093501444194621777L;

	public CredentialsNotFoundException() {
		super("Credentials not found!");
	}

}

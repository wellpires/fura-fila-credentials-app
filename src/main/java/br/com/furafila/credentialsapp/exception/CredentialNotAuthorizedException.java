package br.com.furafila.credentialsapp.exception;

public class CredentialNotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = -2248077046554922967L;

	public CredentialNotAuthorizedException() {
		super("Credentials not authorized!");
	}

}

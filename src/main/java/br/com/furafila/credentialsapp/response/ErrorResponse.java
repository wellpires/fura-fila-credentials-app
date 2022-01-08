package br.com.furafila.credentialsapp.response;

public class ErrorResponse {

	private String message;

	public ErrorResponse(String errorMessage) {
		message = errorMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

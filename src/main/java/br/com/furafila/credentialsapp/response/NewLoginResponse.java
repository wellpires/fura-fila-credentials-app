package br.com.furafila.credentialsapp.response;

public class NewLoginResponse {

	private Long id;

	public NewLoginResponse(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

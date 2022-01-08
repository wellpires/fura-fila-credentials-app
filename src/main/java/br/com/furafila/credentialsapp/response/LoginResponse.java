package br.com.furafila.credentialsapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.credentialsapp.dto.CredentialsDTO;

public class LoginResponse {

	@JsonProperty("credentials")
	private CredentialsDTO credentialsDTO;

	public LoginResponse(CredentialsDTO credentialsDTO) {
		this.credentialsDTO = credentialsDTO;
	}

	public CredentialsDTO getCredentialsDTO() {
		return credentialsDTO;
	}

	public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
		this.credentialsDTO = credentialsDTO;
	}

}

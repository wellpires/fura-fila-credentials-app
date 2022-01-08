package br.com.furafila.credentialsapp.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.credentialsapp.dto.CredentialDTO;

public class LoginResponse {

	@JsonProperty("credential")
	private CredentialDTO credentialDTO;

	public LoginResponse(CredentialDTO credentialsDTO) {
		this.credentialDTO = credentialsDTO;
	}

	public CredentialDTO getCredentialDTO() {
		return credentialDTO;
	}

	public void setCredentialDTO(CredentialDTO credentialDTO) {
		this.credentialDTO = credentialDTO;
	}

}

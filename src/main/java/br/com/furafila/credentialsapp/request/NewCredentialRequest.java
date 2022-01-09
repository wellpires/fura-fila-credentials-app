package br.com.furafila.credentialsapp.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.credentialsapp.dto.NewCredentialDTO;

public class NewCredentialRequest {

	@JsonProperty("credential")
	private NewCredentialDTO newCredentialDTO;

	public NewCredentialDTO getNewCredentialDTO() {
		return newCredentialDTO;
	}

	public void setNewCredentialDTO(NewCredentialDTO newCredentialDTO) {
		this.newCredentialDTO = newCredentialDTO;
	}

}

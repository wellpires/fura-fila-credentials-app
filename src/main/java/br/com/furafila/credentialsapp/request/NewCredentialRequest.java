package br.com.furafila.credentialsapp.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.credentialsapp.dto.NewCredentialDTO;
import br.com.furafila.credentialsapp.util.Messages;

public class NewCredentialRequest {

	@JsonProperty("credential")
	@Valid
	@NotNull(message = Messages.CREDENTIAL_INFO_IS_REQUIRED)
	private NewCredentialDTO newCredentialDTO;

	public NewCredentialDTO getNewCredentialDTO() {
		return newCredentialDTO;
	}

	public void setNewCredentialDTO(NewCredentialDTO newCredentialDTO) {
		this.newCredentialDTO = newCredentialDTO;
	}

}

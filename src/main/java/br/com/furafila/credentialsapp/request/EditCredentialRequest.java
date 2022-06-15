package br.com.furafila.credentialsapp.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.credentialsapp.dto.EditCredentialDTO;
import br.com.furafila.credentialsapp.util.Messages;


public class EditCredentialRequest {

	@JsonProperty("credential")
	@NotNull(message = Messages.EDIT_CREDENTIAL_INFO_IS_REQUIRED)
	@Valid
	private EditCredentialDTO editCredentialDTO;

	public EditCredentialDTO getEditCredentialDTO() {
		return editCredentialDTO;
	}

	public void setEditCredentialDTO(EditCredentialDTO editCredentialDTO) {
		this.editCredentialDTO = editCredentialDTO;
	}

}

package br.com.furafila.credentialsapp.dto;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.furafila.credentialsapp.util.Messages;
import br.com.furafila.credentialsapp.validation.order.FirstOrder;
import br.com.furafila.credentialsapp.validation.order.SecondOrder;

@GroupSequence({ EditCredentialDTO.class, FirstOrder.class, SecondOrder.class })
public class EditCredentialDTO {

	@NotBlank(message = Messages.EDIT_USERNAME_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 4, max = 50, message = Messages.EDIT_USERNAME_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String username;

	@NotBlank(message = Messages.EDIT_PASSWORD_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 8, max = 32, message = Messages.EDIT_PASSWORD_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

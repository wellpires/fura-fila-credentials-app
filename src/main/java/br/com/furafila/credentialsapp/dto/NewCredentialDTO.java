package br.com.furafila.credentialsapp.dto;

import javax.validation.GroupSequence;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.furafila.credentialsapp.util.Messages;
import br.com.furafila.credentialsapp.validation.order.FirstOrder;
import br.com.furafila.credentialsapp.validation.order.SecondOrder;

@GroupSequence({ NewCredentialDTO.class, FirstOrder.class, SecondOrder.class })
public class NewCredentialDTO {

	@NotBlank(message = Messages.USERNAME_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 4, max = 50, message = Messages.USERNAME_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String username;

	@NotBlank(message = Messages.PASSWORD_IS_REQUIRED, groups = FirstOrder.class)
	@Size(min = 8, max = 32, message = Messages.PASSWORD_LENGTH_IS_NOT_VALID, groups = SecondOrder.class)
	private String password;

	@NotNull(message = Messages.STATUS_IS_REQUIRED)
	private Boolean status;

	@NotNull(message = Messages.DELIVERY_AVAILABLE_IS_REQUIRED)
	private Boolean deliveryAvailable;

	@NotNull(message = Messages.LEVEL_ID_IS_REQUIRED, groups = FirstOrder.class)
	@DecimalMin(value = "1", message = Messages.LEVEL_ID_IS_NOT_VALID, groups = SecondOrder.class)
	private Long levelId;

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getDeliveryAvailable() {
		return deliveryAvailable;
	}

	public void setDeliveryAvailable(Boolean deliveryAvailable) {
		this.deliveryAvailable = deliveryAvailable;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

}

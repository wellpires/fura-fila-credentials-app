package br.com.furafila.credentialsapp.dto;

public class NewCredentialDTO {

	private String username;
	private String password;
	private Boolean status;
	private Boolean deliveryAvailable;
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

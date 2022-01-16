package br.com.furafila.credentialsapp.response;

public class CredentialDuplicityResponse {

	private Boolean isCredentialExist;

	public CredentialDuplicityResponse() {
	}

	public CredentialDuplicityResponse(Boolean isCredentialExist) {
		this.isCredentialExist = isCredentialExist;
	}

	public Boolean getIsCredentialExist() {
		return isCredentialExist;
	}

	public void setIsCredentialExist(Boolean isCredentialExist) {
		this.isCredentialExist = isCredentialExist;
	}

}

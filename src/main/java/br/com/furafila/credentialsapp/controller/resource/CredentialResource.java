package br.com.furafila.credentialsapp.controller.resource;

import org.springframework.http.ResponseEntity;

import br.com.furafila.credentialsapp.request.NewCredentialRequest;
import br.com.furafila.credentialsapp.response.CouriersResponse;
import br.com.furafila.credentialsapp.response.CredentialDuplicityResponse;

public interface CredentialResource {

	public ResponseEntity<CredentialDuplicityResponse> checkCredentialsDuplicity(Long id, String username, Boolean include);

	public ResponseEntity<CouriersResponse> listCouriers();
	
	public ResponseEntity<Void> saveCredential(NewCredentialRequest newCredentialRequest);
	
}

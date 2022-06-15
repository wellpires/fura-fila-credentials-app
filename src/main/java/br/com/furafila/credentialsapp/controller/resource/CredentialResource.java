package br.com.furafila.credentialsapp.controller.resource;

import org.springframework.http.ResponseEntity;

import br.com.furafila.credentialsapp.request.EditCredentialRequest;
import br.com.furafila.credentialsapp.request.NewCredentialRequest;
import br.com.furafila.credentialsapp.response.CouriersResponse;
import br.com.furafila.credentialsapp.response.CredentialDuplicityResponse;
import br.com.furafila.credentialsapp.response.NewLoginResponse;

public interface CredentialResource {

	public ResponseEntity<CredentialDuplicityResponse> checkCredentialsDuplicity(Long id, String username,
			Boolean include);

	public ResponseEntity<CouriersResponse> listCouriers();

	public ResponseEntity<NewLoginResponse> saveCredential(NewCredentialRequest newCredentialRequest);

	public ResponseEntity<Void> editCredential(Long loginId, EditCredentialRequest editCredentialRequest);

	public ResponseEntity<Void> deleteCredential(Long loginId);

}

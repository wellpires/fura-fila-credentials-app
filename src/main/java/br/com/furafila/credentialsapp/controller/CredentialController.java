package br.com.furafila.credentialsapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.furafila.credentialsapp.controller.resource.CredentialResource;
import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.request.NewCredentialRequest;
import br.com.furafila.credentialsapp.response.CouriersResponse;
import br.com.furafila.credentialsapp.response.CredentialDuplicityResponse;
import br.com.furafila.credentialsapp.service.CredentialsService;

@RestController
@RequestMapping("credential")
public class CredentialController implements CredentialResource {

	@Autowired
	private CredentialsService credentialsService;

	@Override
	@GetMapping(path = "id/{id}/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CredentialDuplicityResponse> checkCredentialsDuplicity(@PathVariable("id") Long id,
			@PathVariable("username") String username, @RequestParam("include") Boolean include) {

		Boolean credentialsExists = credentialsService.checkCredentialsDuplicity(id, username, include);

		return ResponseEntity.ok(new CredentialDuplicityResponse(credentialsExists));
	}

	@Override
	@GetMapping(path = "couriers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CouriersResponse> listCouriers() {
			
		List<CourierDTO> couriers = credentialsService.listAllCouriers();
					
		return ResponseEntity.ok(new CouriersResponse(couriers));
	}

	@Override
	public ResponseEntity<Void> saveCredential(NewCredentialRequest newCredentialRequest) {
		
//		credentialsService.saveCredential(newCredentialRequest)
		
		return null;
	}

}

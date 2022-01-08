package br.com.furafila.credentialsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.furafila.credentialsapp.controller.resource.AuthResource;
import br.com.furafila.credentialsapp.dto.CredentialDTO;
import br.com.furafila.credentialsapp.response.LoginResponse;
import br.com.furafila.credentialsapp.service.CredentialsService;

@RestController
@RequestMapping("auth")
public class AuthController implements AuthResource {

	@Autowired
	private CredentialsService credentialsService;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> validateCredentials(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		CredentialDTO credentialsValidated = credentialsService.validateCredentials(username, password);
		return ResponseEntity.ok(new LoginResponse(credentialsValidated));
	}

}

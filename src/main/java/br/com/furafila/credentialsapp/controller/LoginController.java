package br.com.furafila.credentialsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.furafila.credentialsapp.controller.resource.LoginResource;
import br.com.furafila.credentialsapp.dto.CredentialsDTO;
import br.com.furafila.credentialsapp.response.LoginResponse;
import br.com.furafila.credentialsapp.service.CredentialsService;

@RestController
@RequestMapping("v1/validate-credentials")
public class LoginController implements LoginResource {

	@Autowired
	private CredentialsService credentialsService;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> validateCredentials(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		CredentialsDTO credentialsValidated = credentialsService.validateCredentials(username, password);
		return ResponseEntity.ok(new LoginResponse(credentialsValidated));
	}

}

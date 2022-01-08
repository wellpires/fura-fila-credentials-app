package br.com.furafila.credentialsapp.controller.resource;

import org.springframework.http.ResponseEntity;

import br.com.furafila.credentialsapp.response.LoginResponse;

public interface LoginResource {
	
	public ResponseEntity<LoginResponse> validateCredentials(String username, String password);
	
	
}

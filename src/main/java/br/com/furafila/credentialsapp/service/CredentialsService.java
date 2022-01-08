package br.com.furafila.credentialsapp.service;

import br.com.furafila.credentialsapp.dto.CredentialsDTO;

public interface CredentialsService {

	public CredentialsDTO validateCredentials(String username, String password);
	
}

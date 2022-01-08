package br.com.furafila.credentialsapp.service;

import java.util.List;

import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.dto.CredentialDTO;

public interface CredentialsService {

	public CredentialDTO validateCredentials(String username, String password);

	public Boolean checkCredentialsDuplicity(Long id, String username, Boolean include);

	public List<CourierDTO> listAllCouriers();
	
}

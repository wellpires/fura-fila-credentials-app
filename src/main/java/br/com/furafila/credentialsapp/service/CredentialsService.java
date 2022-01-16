package br.com.furafila.credentialsapp.service;

import java.util.List;

import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.dto.CredentialDTO;
import br.com.furafila.credentialsapp.dto.NewCredentialDTO;

public interface CredentialsService {

	public CredentialDTO validateCredentials(String username, String password);

	public boolean checkCredentialsDuplicity(Long id, String username, Boolean include);

	public List<CourierDTO> listAllCouriers();

	public Long saveCredential(NewCredentialDTO newCredentialDTO);
	
}

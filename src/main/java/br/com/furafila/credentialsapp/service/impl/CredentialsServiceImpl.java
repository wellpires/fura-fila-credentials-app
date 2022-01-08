package br.com.furafila.credentialsapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.furafila.credentialsapp.build.CredentialsDTOBuilder;
import br.com.furafila.credentialsapp.dto.CredentialsDTO;
import br.com.furafila.credentialsapp.exception.CredentialsNotFoundException;
import br.com.furafila.credentialsapp.model.Login;
import br.com.furafila.credentialsapp.repository.CredentialsRepository;
import br.com.furafila.credentialsapp.service.CredentialsService;

@Service
public class CredentialsServiceImpl implements CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Override
	public CredentialsDTO validateCredentials(String username, String password) {

		Login credentials = credentialsRepository.findCredentials(username, password)
				.orElseThrow(CredentialsNotFoundException::new);

		CredentialsDTO credentialsDTO = new CredentialsDTO();
		if (username.equals(credentials.getUsername()) && password.equals(credentials.getPassword())) {
			credentialsDTO = new CredentialsDTOBuilder().idLogin(credentials.getId())
					.username(credentials.getUsername()).password(credentials.getPassword())
					.idLevel(credentials.getPermissao().getId()).levelInitials(credentials.getPermissao().getInitials())
					.level(credentials.getPermissao().getLevel()).build();

		}

		return credentialsDTO;
	}

}

package br.com.furafila.credentialsapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.furafila.credentialsapp.builder.CredentialsDTOBuilder;
import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.dto.CredentialDTO;
import br.com.furafila.credentialsapp.exception.CredentialsNotFoundException;
import br.com.furafila.credentialsapp.function.Login2CouriersDTOFunction;
import br.com.furafila.credentialsapp.model.Login;
import br.com.furafila.credentialsapp.repository.CredentialsRepository;
import br.com.furafila.credentialsapp.service.CredentialsService;

@Service
public class CredentialsServiceImpl implements CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Override
	public CredentialDTO validateCredentials(String username, String password) {

		Login credentials = credentialsRepository.findCredentials(username, password)
				.orElseThrow(CredentialsNotFoundException::new);

		CredentialDTO credentialsDTO = new CredentialDTO();
		if (username.equals(credentials.getUsername()) && password.equals(credentials.getPassword())) {
			credentialsDTO = new CredentialsDTOBuilder().idLogin(credentials.getId())
					.username(credentials.getUsername()).password(credentials.getPassword())
					.idLevel(credentials.getPermissao().getId()).levelInitials(credentials.getPermissao().getInitials())
					.level(credentials.getPermissao().getLevel()).build();

		}

		return credentialsDTO;
	}

	@Override
	public Boolean checkCredentialsDuplicity(Long id, String username, Boolean include) {

		Boolean result = true;
		List<Login> login = credentialsRepository.findLoginByUsername(username);

		if (CollectionUtils.isEmpty(login)) {
			result = false;
		}

		if (include) {
			long count = login.stream().filter(item -> item.getId() != id).count();
			result = count > 0;
		}

		return result;
	}

	@Override
	public List<CourierDTO> listAllCouriers() {

		List<Login> couriers = credentialsRepository.findAllCouriers("E");

		return couriers.stream().map(new Login2CouriersDTOFunction()).collect(Collectors.toList());
	}

}

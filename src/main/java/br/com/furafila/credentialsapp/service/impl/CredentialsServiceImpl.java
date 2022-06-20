package br.com.furafila.credentialsapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.com.furafila.credentialsapp.builder.CredentialsDTOBuilder;
import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.dto.CredentialDTO;
import br.com.furafila.credentialsapp.dto.EditCredentialDTO;
import br.com.furafila.credentialsapp.dto.NewCredentialDTO;
import br.com.furafila.credentialsapp.exception.CredentialNotAuthorizedException;
import br.com.furafila.credentialsapp.exception.CredentialsNotFoundException;
import br.com.furafila.credentialsapp.function.Login2CouriersDTOFunction;
import br.com.furafila.credentialsapp.model.Login;
import br.com.furafila.credentialsapp.model.Permissao;
import br.com.furafila.credentialsapp.repository.CredentialsRepository;
import br.com.furafila.credentialsapp.service.CredentialsService;
import br.com.furafila.credentialsapp.util.Constants;

@Service
public class CredentialsServiceImpl implements CredentialsService {

	@Autowired
	private CredentialsRepository credentialsRepository;

	@Override
	public CredentialDTO validateCredentials(String username, String password) {

		Login credentials = credentialsRepository.findCredentials(username, password)
				.orElseThrow(CredentialsNotFoundException::new);

		if (!username.equals(credentials.getUsername()) || !password.equals(credentials.getPassword())) {
			throw new CredentialNotAuthorizedException();
		}

		CredentialDTO credentialsDTO = new CredentialsDTOBuilder().idLogin(credentials.getId())
				.username(credentials.getUsername()).password(credentials.getPassword())
				.idLevel(credentials.getPermissao().getId()).levelInitials(credentials.getPermissao().getInitials())
				.level(credentials.getPermissao().getLevel()).build();

		return credentialsDTO;
	}

	@Override
	public boolean checkCredentialsDuplicity(Long id, String username, Boolean include) {

		List<Login> login = credentialsRepository.findLoginByUsername(username);

		boolean result = true;
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

		List<Login> couriers = credentialsRepository.findAllCouriers(Constants.ENTREGADOR_INITIAL);

		return couriers.stream().map(new Login2CouriersDTOFunction()).collect(Collectors.toList());
	}

	@Override
	public Long saveCredential(NewCredentialDTO newCredentialDTO) {

		Login login = new Login();
		login.setUsername(newCredentialDTO.getUsername());
		login.setPassword(newCredentialDTO.getPassword());
		login.setDeliveryAvailable(newCredentialDTO.getDeliveryAvailable());
		login.setStatus(newCredentialDTO.getStatus());

		Permissao permissao = new Permissao();
		permissao.setId(newCredentialDTO.getLevelId());

		login.setPermissao(permissao);

		Login newLogin = credentialsRepository.save(login);

		return newLogin.getId();
	}

	@Override
	public void editCredential(Long loginId, EditCredentialDTO editCredentialDTO) {

		Login login = credentialsRepository.findById(loginId).orElseThrow(CredentialsNotFoundException::new);

		login.setUsername(editCredentialDTO.getUsername());
		login.setPassword(editCredentialDTO.getPassword());

		credentialsRepository.save(login);

	}

	@Override
	public void deleteCredential(Long loginId) {

		Login login = credentialsRepository.findById(loginId).orElseThrow(CredentialsNotFoundException::new);

		credentialsRepository.delete(login);
	}

	@Override
	public void toggleCourierStatus(Long loginId) {

		Login login = credentialsRepository.findById(loginId).orElseThrow(CredentialsNotFoundException::new);

		login.setStatus(!login.getStatus());

		credentialsRepository.save(login);

	}

	@Override
	public void toggleCourierAvailability(Long loginId) {

		Login login = credentialsRepository.findById(loginId).orElseThrow(CredentialsNotFoundException::new);

		login.setDeliveryAvailable(!login.getDeliveryAvailable());

		credentialsRepository.save(login);
	}

}

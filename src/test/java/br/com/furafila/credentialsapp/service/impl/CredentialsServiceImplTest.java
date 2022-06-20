package br.com.furafila.credentialsapp.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.dto.CredentialDTO;
import br.com.furafila.credentialsapp.dto.EditCredentialDTO;
import br.com.furafila.credentialsapp.dto.NewCredentialDTO;
import br.com.furafila.credentialsapp.exception.CredentialNotAuthorizedException;
import br.com.furafila.credentialsapp.exception.CredentialsNotFoundException;
import br.com.furafila.credentialsapp.model.Login;
import br.com.furafila.credentialsapp.model.Permissao;
import br.com.furafila.credentialsapp.repository.CredentialsRepository;
import br.com.furafila.credentialsapp.service.CredentialsService;
import br.com.furafila.credentialsapp.util.ReplaceCamelCase;

@DisplayNameGeneration(ReplaceCamelCase.class)
@ExtendWith(MockitoExtension.class)
public class CredentialsServiceImplTest {

	@InjectMocks
	private CredentialsService credentialsService = new CredentialsServiceImpl();

	@Mock
	private CredentialsRepository credentialsRepository;

	@Test
	public void shouldValidateCredentials() {

		String username = "usernameTeste";
		String password = "123456";

		Login login = new Login();
		login.setId(1l);
		login.setDeliveryAvailable(Boolean.FALSE);
		login.setUsername(username);
		login.setPassword(password);
		login.setStatus(Boolean.TRUE);

		Permissao permissao = new Permissao();
		permissao.setId(1l);
		permissao.setInitials("A");
		permissao.setLevel("Administrador");
		login.setPermissao(permissao);

		when(credentialsRepository.findCredentials(anyString(), anyString())).thenReturn(Optional.ofNullable(login));

		CredentialDTO credentialDTO = credentialsService.validateCredentials(username, password);

		assertThat(login.getId(), equalTo(credentialDTO.getId()));
		assertThat(login.getUsername(), equalTo(credentialDTO.getUsername()));
		assertThat(login.getPassword(), equalTo(credentialDTO.getPassword()));
		assertThat(login.getPermissao().getId(), equalTo(credentialDTO.getId()));
		assertThat(login.getPermissao().getLevel(), equalTo(credentialDTO.getLevel()));
		assertThat(login.getPermissao().getId(), equalTo(credentialDTO.getLevelId()));

	}

	@Test
	public void shouldValidateCredentialsButNotMatchCredentials() {

		String username = "usernameTeste";
		String password = "123456";

		Login login = new Login();
		login.setId(1l);
		login.setDeliveryAvailable(Boolean.FALSE);
		login.setUsername(username);
		login.setPassword(password);
		login.setStatus(Boolean.TRUE);

		Permissao permissao = new Permissao();
		permissao.setId(1l);
		permissao.setInitials("A");
		permissao.setLevel("Administrador");
		login.setPermissao(permissao);

		when(credentialsRepository.findCredentials(anyString(), anyString())).thenReturn(Optional.ofNullable(login));

		Assertions.assertThrows(CredentialNotAuthorizedException.class, () -> {
			String username_2 = "usernameTeste_2";
			String password_2 = "123456_2";
			credentialsService.validateCredentials(username_2, password_2);
		});

	}

	@Test
	public void shouldValidateCredentialsButNotMatchPassword() {

		String username = "usernameTeste";
		String password = "123456";

		Login login = new Login();
		login.setId(1l);
		login.setDeliveryAvailable(Boolean.FALSE);
		login.setUsername(username);
		login.setPassword(password);
		login.setStatus(Boolean.TRUE);

		Permissao permissao = new Permissao();
		permissao.setId(1l);
		permissao.setInitials("A");
		permissao.setLevel("Administrador");
		login.setPermissao(permissao);

		when(credentialsRepository.findCredentials(anyString(), anyString())).thenReturn(Optional.ofNullable(login));

		Assertions.assertThrows(CredentialNotAuthorizedException.class, () -> {
			String password_2 = "123456_2";
			credentialsService.validateCredentials(username, password_2);
		});

	}

	@Test
	public void shouldNotValidateCredentialsBecauseCredentialsNotFound() {

		Login login = null;
		when(credentialsRepository.findCredentials(anyString(), anyString())).thenReturn(Optional.ofNullable(login));

		Assertions.assertThrows(CredentialsNotFoundException.class, () -> {
			credentialsService.validateCredentials("usernameTeste", "123456");
		});

	}

	@Test
	public void shouldCheckCredentialsDuplicity() {

		Login login2 = new Login();
		Login login1 = new Login();
		List<Login> logins = Arrays.asList(login1, login2);
		when(credentialsRepository.findLoginByUsername(anyString())).thenReturn(logins);

		boolean isDuplicity = credentialsService.checkCredentialsDuplicity(1l, "username", Boolean.FALSE);

		assertTrue(isDuplicity);

	}

	@Test
	public void shouldNotCheckDuplicityBecauseCredentialNotFound() {

		List<Login> logins = Arrays.asList();
		when(credentialsRepository.findLoginByUsername(anyString())).thenReturn(logins);

		boolean isDuplicity = credentialsService.checkCredentialsDuplicity(1l, "username", Boolean.TRUE);

		assertFalse(isDuplicity);

	}

	@Test
	public void shouldCheckCredentialsDuplicityWithoutItself() {

		Login login1 = new Login();
		login1.setId(1l);

		Login login2 = new Login();
		login2.setId(2l);

		List<Login> logins = Arrays.asList(login1, login2);
		when(credentialsRepository.findLoginByUsername(anyString())).thenReturn(logins);

		boolean isDuplicity = credentialsService.checkCredentialsDuplicity(1l, "username", Boolean.TRUE);

		assertTrue(isDuplicity);

	}

	@Test
	public void shouldListAllCouriers() {

		Login login1 = new Login();
		login1.setId(1l);
		login1.setUsername("usernameTest");
		login1.setStatus(Boolean.TRUE);
		login1.setDeliveryAvailable(Boolean.TRUE);

		Login login2 = new Login();
		login2.setId(2l);
		login2.setUsername("usernameTest_2");
		login2.setStatus(Boolean.TRUE);
		login2.setDeliveryAvailable(Boolean.TRUE);

		List<Login> logins = Arrays.asList(login1, login2);
		when(credentialsRepository.findAllCouriers(anyString())).thenReturn(logins);

		List<CourierDTO> couriers = credentialsService.listAllCouriers();

		assertThat(couriers, hasSize(2));

		assertThat(couriers.get(0).getId(), equalTo(logins.get(0).getId()));
		assertThat(couriers.get(0).getUsername(), equalTo(logins.get(0).getUsername()));
		assertThat(couriers.get(0).getStatus(), equalTo(logins.get(0).getStatus()));
		assertThat(couriers.get(0).getDeliveryAvailable(), equalTo(logins.get(0).getDeliveryAvailable()));

		assertThat(couriers.get(1).getId(), equalTo(logins.get(1).getId()));
		assertThat(couriers.get(1).getUsername(), equalTo(logins.get(1).getUsername()));
		assertThat(couriers.get(1).getStatus(), equalTo(logins.get(1).getStatus()));
		assertThat(couriers.get(1).getDeliveryAvailable(), equalTo(logins.get(1).getDeliveryAvailable()));
	}

	@Test
	public void shouldSaveCredential() {

		NewCredentialDTO newCredentialDTO = new NewCredentialDTO();

		newCredentialDTO.setUsername("username_test");
		newCredentialDTO.setPassword("123456");
		newCredentialDTO.setDeliveryAvailable(Boolean.TRUE);
		newCredentialDTO.setStatus(Boolean.TRUE);
		newCredentialDTO.setLevelId(1l);

		long idLogin = 1l;
		Login login = new Login();
		login.setId(idLogin);
		when(credentialsRepository.save(any())).thenReturn(login);

		Long idCredential = credentialsService.saveCredential(newCredentialDTO);

		ArgumentCaptor<Login> captor = ArgumentCaptor.forClass(Login.class);
		verify(credentialsRepository).save(captor.capture());
		Login loginCaptor = captor.getValue();

		assertThat(loginCaptor.getUsername(), equalTo(newCredentialDTO.getUsername()));
		assertThat(loginCaptor.getPassword(), equalTo(newCredentialDTO.getPassword()));
		assertThat(loginCaptor.getDeliveryAvailable(), equalTo(newCredentialDTO.getDeliveryAvailable()));
		assertThat(loginCaptor.getStatus(), equalTo(newCredentialDTO.getStatus()));
		assertNotNull(loginCaptor.getPermissao());
		assertThat(loginCaptor.getPermissao().getId(), equalTo(newCredentialDTO.getLevelId()));

		assertThat(idCredential, equalTo(idLogin));

	}

	@Test
	public void shouldEditCredential() {

		Login login = new Login();
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		EditCredentialDTO editCredentialDTO = new EditCredentialDTO();
		editCredentialDTO.setUsername("username 1");
		editCredentialDTO.setPassword("123321");
		credentialsService.editCredential(12L, editCredentialDTO);

		ArgumentCaptor<Login> captor = ArgumentCaptor.forClass(Login.class);
		verify(credentialsRepository).save(captor.capture());
		Login loginCaptor = captor.getValue();

		assertThat(loginCaptor.getUsername(), equalTo(editCredentialDTO.getUsername()));
		assertThat(loginCaptor.getPassword(), equalTo(editCredentialDTO.getPassword()));

	}

	@Test
	public void shouldNotEditCredentialBecauseCredentialNotFound() {

		when(credentialsRepository.findById(anyLong())).thenThrow(new CredentialsNotFoundException());

		EditCredentialDTO editCredentialDTO = new EditCredentialDTO();
		editCredentialDTO.setUsername("username 1");
		editCredentialDTO.setPassword("123321");

		assertThrows(CredentialsNotFoundException.class, () -> {
			credentialsService.editCredential(12L, editCredentialDTO);
		});

		verify(credentialsRepository, never()).save(any());

	}

	@Test
	public void shouldDeleteCredential() {

		Login login = new Login();
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		credentialsService.deleteCredential(123l);

		verify(credentialsRepository, times(1)).delete(any());

	}

	@Test
	public void shouldNotDeleteCredentialBecauseCredentialNotFound() {

		when(credentialsRepository.findById(anyLong())).thenThrow(new CredentialsNotFoundException());

		assertThrows(CredentialsNotFoundException.class, () -> {
			credentialsService.deleteCredential(123l);
		});

		verify(credentialsRepository, never()).delete(any());

	}

	@Test
	public void shouldToggleCourierStatusToFalse() {

		Login login = new Login();
		login.setStatus(Boolean.TRUE);
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		credentialsService.toggleCourierStatus(10l);

		ArgumentCaptor<Login> loginCaptor = ArgumentCaptor.forClass(Login.class);
		verify(credentialsRepository).save(loginCaptor.capture());

		Login loginCaught = loginCaptor.getValue();

		assertFalse(loginCaught.getStatus());

	}

	@Test
	public void shouldToggleCourierStatusToTrue() {

		Login login = new Login();
		login.setStatus(Boolean.FALSE);
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		credentialsService.toggleCourierStatus(10l);

		ArgumentCaptor<Login> loginCaptor = ArgumentCaptor.forClass(Login.class);
		verify(credentialsRepository).save(loginCaptor.capture());

		Login loginCaught = loginCaptor.getValue();

		assertTrue(loginCaught.getStatus());

	}

	@Test
	public void shouldTNotoggleCourierStatusBecauseCredentialsNotFound() {

		Login login = null;
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		assertThrows(CredentialsNotFoundException.class, () -> {
			credentialsService.toggleCourierStatus(10l);
		});

		verify(credentialsRepository, never()).save(any(Login.class));

	}

	@Test
	public void shouldToggleCourierAvailabilityToFalse() {

		Login login = new Login();
		login.setDeliveryAvailable(Boolean.TRUE);
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		credentialsService.toggleCourierAvailability(10l);

		ArgumentCaptor<Login> loginCaptor = ArgumentCaptor.forClass(Login.class);
		verify(credentialsRepository).save(loginCaptor.capture());

		Login loginCaught = loginCaptor.getValue();

		assertFalse(loginCaught.getDeliveryAvailable());

	}

	@Test
	public void shouldToggleCourierAvailabilityToTrue() {

		Login login = new Login();
		login.setDeliveryAvailable(Boolean.FALSE);
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		credentialsService.toggleCourierAvailability(10l);

		ArgumentCaptor<Login> loginCaptor = ArgumentCaptor.forClass(Login.class);
		verify(credentialsRepository).save(loginCaptor.capture());

		Login loginCaught = loginCaptor.getValue();

		assertTrue(loginCaught.getDeliveryAvailable());

	}

	@Test
	public void shouldNotToggleCourierAvailabilityBecauseCredentialsNotFound() {

		Login login = null;
		when(credentialsRepository.findById(anyLong())).thenReturn(Optional.ofNullable(login));

		assertThrows(CredentialsNotFoundException.class, () -> {
			credentialsService.toggleCourierAvailability(10l);
		});

		verify(credentialsRepository, never()).save(any(Login.class));

	}

}

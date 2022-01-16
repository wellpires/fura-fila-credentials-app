package br.com.furafila.credentialsapp.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.furafila.credentialsapp.builder.CredentialsDTOBuilder;
import br.com.furafila.credentialsapp.dto.CredentialDTO;
import br.com.furafila.credentialsapp.exception.CredentialNotAuthorizedException;
import br.com.furafila.credentialsapp.exception.CredentialsNotFoundException;
import br.com.furafila.credentialsapp.response.LoginResponse;
import br.com.furafila.credentialsapp.service.CredentialsService;
import br.com.furafila.credentialsapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class AuthControllerTest {

	private static final String AUTH_URL = "/auth";

	@MockBean
	private CredentialsService credentialsService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void shouldValidateCredentials() throws Exception {

		String username = "username_teste";
		String password = "123456789";

		String uri = UriComponentsBuilder.fromPath(AUTH_URL).queryParam("username", username)
				.queryParam("password", password).build().toUriString();

		CredentialDTO credentialDTO = new CredentialsDTOBuilder().idLogin(1l).username(username).password(password)
				.idLevel(1l).levelInitials("A").level("Administrador").build();
		when(credentialsService.validateCredentials(anyString(), anyString())).thenReturn(credentialDTO);

		MvcResult result = mockMvc.perform(get(uri)).andExpect(status().isOk()).andDo(print()).andReturn();

		LoginResponse loginResponse = mapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class);

		assertThat(loginResponse.getCredentialDTO().getUsername(), equalTo(username));
		assertThat(loginResponse.getCredentialDTO().getPassword(), equalTo(password));

		assertThat(credentialDTO.getId(), equalTo(loginResponse.getCredentialDTO().getId()));
		assertThat(credentialDTO.getInitials(), equalTo(loginResponse.getCredentialDTO().getInitials()));
		assertThat(credentialDTO.getLevel(), equalTo(loginResponse.getCredentialDTO().getLevel()));
		assertThat(credentialDTO.getLevelId(), equalTo(loginResponse.getCredentialDTO().getLevelId()));

	}

	@Test
	public void shouldNotValidateCredentialsBecauseCredentialsNotFound() throws Exception {

		String username = "username_teste";
		String password = "123456789";

		String uri = UriComponentsBuilder.fromPath(AUTH_URL).queryParam("username", username)
				.queryParam("password", password).build().toUriString();

		doThrow(CredentialsNotFoundException.class).when(credentialsService).validateCredentials(anyString(),
				anyString());

		mockMvc.perform(get(uri)).andExpect(status().isNotFound()).andDo(print());

	}

	@Test
	public void shouldNotValidateCredentialBecauseDoesntMatch() throws Exception {

		String username = "username_teste";
		String password = "123456789";

		String uri = UriComponentsBuilder.fromPath(AUTH_URL).queryParam("username", username)
				.queryParam("password", password).build().toUriString();

		doThrow(CredentialNotAuthorizedException.class).when(credentialsService).validateCredentials(anyString(),
				anyString());

		mockMvc.perform(get(uri)).andExpect(status().isUnauthorized()).andDo(print());

	}

}

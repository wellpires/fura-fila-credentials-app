package br.com.furafila.credentialsapp.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.request.EditCredentialRequest;
import br.com.furafila.credentialsapp.request.NewCredentialRequest;
import br.com.furafila.credentialsapp.response.CouriersResponse;
import br.com.furafila.credentialsapp.response.CredentialDuplicityResponse;
import br.com.furafila.credentialsapp.response.ErrorResponse;
import br.com.furafila.credentialsapp.response.NewLoginResponse;
import br.com.furafila.credentialsapp.service.CredentialsService;
import br.com.furafila.credentialsapp.util.Messages;
import br.com.furafila.credentialsapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CredentialController.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class CredentialControllerTest {

	private static final String CREDENTIAL_PATH = "/credential";
	private static final String CREDENTIAL__DUPLICITY_URL = CREDENTIAL_PATH.concat("/id/{id}/username/{username}");
	private static final String LIST_COURIERS_URL = CREDENTIAL_PATH.concat("/couriers");
	private static final String EDIT_CREDENTIAL_PATH = CREDENTIAL_PATH.concat("/{loginId}");
	private static final String DELETE_CREDENTIAL_PATH = CREDENTIAL_PATH.concat("/{loginId}");

	@MockBean
	private CredentialsService credentialsService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private NewCredentialRequest newCredentialRequest;
	private EditCredentialRequest editCredentialRequest;

	@BeforeEach
	public void setup() throws StreamReadException, DatabindException, IOException {
		newCredentialRequest = mapper.readValue(
				Paths.get("src", "test", "resources", "NewCredentialRequest.json").toFile(),
				NewCredentialRequest.class);

		editCredentialRequest = mapper.readValue(
				Paths.get("src", "test", "resources", "NewCredentialRequest.json").toFile(),
				EditCredentialRequest.class);

	}

	@Test
	public void shouldCheckCredentialsDuplicity() throws Exception {

		when(credentialsService.checkCredentialsDuplicity(anyLong(), anyString(), anyBoolean()))
				.thenReturn(Boolean.FALSE);

		Map<String, Object> params = new HashMap<>();
		params.put("id", 1l);
		params.put("username", "user_teste");

		URI uri = UriComponentsBuilder.fromPath(CREDENTIAL__DUPLICITY_URL).buildAndExpand(params).toUri();
		MvcResult result = mockMvc.perform(get(uri)).andExpect(status().isOk()).andReturn();
		CredentialDuplicityResponse credentialDuplicityResponse = mapper
				.readValue(result.getResponse().getContentAsString(), CredentialDuplicityResponse.class);

		assertNotNull(credentialDuplicityResponse);
		assertFalse(credentialDuplicityResponse.getIsCredentialExist());

	}

	@Test
	public void shouldListCouriers() throws Exception {

		List<CourierDTO> asList = Arrays.asList(new CourierDTO(), new CourierDTO(), new CourierDTO());
		when(credentialsService.listAllCouriers()).thenReturn(asList);

		MvcResult result = mockMvc.perform(get(LIST_COURIERS_URL)).andExpect(status().isOk()).andReturn();
		CouriersResponse couriersResponse = mapper.readValue(result.getResponse().getContentAsString(),
				CouriersResponse.class);

		assertThat(couriersResponse.getCouriersDTO(), hasSize(3));

	}

	@Test
	public void shouldSaveCredential() throws Exception {

		long id = 654l;
		when(credentialsService.saveCredential(any())).thenReturn(id);

		String json = mapper.writeValueAsString(newCredentialRequest);

		MvcResult result = mockMvc.perform(post(CREDENTIAL_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn();

		NewLoginResponse newLoginResponse = mapper.readValue(result.getResponse().getContentAsString(),
				NewLoginResponse.class);

		assertNotNull(newLoginResponse);
		assertThat(newLoginResponse.getId(), equalTo(id));

	}

	@Test
	public void shouldNotSaveCredentialBecauseExceptionIsThrown() throws Exception {

		doThrow(new RuntimeException("TESTE ERRO")).when(credentialsService).saveCredential(any());

		String json = mapper.writeValueAsString(newCredentialRequest);

		mockMvc.perform(post(CREDENTIAL_PATH).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isInternalServerError());

	}

	@Test
	public void shouldNotSaveCredentialBecauseCredencialInfoIsRequired() throws Exception {

		newCredentialRequest.setNewCredentialDTO(null);

		testCustomerFieldsValidation(newCredentialRequest, Messages.CREDENTIAL_INFO_IS_REQUIRED);

	}

	@Test
	public void shouldNotSaveCredentialBecauseUsernameIsRequired() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setUsername(null);

		testCustomerFieldsValidation(newCredentialRequest, Messages.USERNAME_IS_REQUIRED);

	}

	@Test
	public void shouldNotSaveCredentialBecauseUsernameIsNotValid() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setUsername("asd");

		testCustomerFieldsValidation(newCredentialRequest, Messages.USERNAME_LENGTH_IS_NOT_VALID);

	}

	@Test
	public void shouldNotSaveCredentialBecausePasswordIsRequired() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setPassword("");

		testCustomerFieldsValidation(newCredentialRequest, Messages.PASSWORD_IS_REQUIRED);

	}

	@Test
	public void shouldNotSaveCredentialBecausePasswordIsNotValid() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setPassword("aa");

		testCustomerFieldsValidation(newCredentialRequest, Messages.PASSWORD_LENGTH_IS_NOT_VALID);

	}

	@Test
	public void shouldNotSaveCredentialBecauseStatusIsRequired() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setStatus(null);

		testCustomerFieldsValidation(newCredentialRequest, Messages.STATUS_IS_REQUIRED);

	}

	@Test
	public void shouldNotSaveCredentialBecauseDeliveryAvailableIsRequired() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setDeliveryAvailable(null);

		testCustomerFieldsValidation(newCredentialRequest, Messages.DELIVERY_AVAILABLE_IS_REQUIRED);

	}

	@Test
	public void shouldNotSaveCredentialBecauseLevelIdIsRequired() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setLevelId(null);

		testCustomerFieldsValidation(newCredentialRequest, Messages.LEVEL_ID_IS_REQUIRED);

	}

	@Test
	public void shouldNotSaveCredentialBecauseLevelIdIsNotValid() throws Exception {

		newCredentialRequest.getNewCredentialDTO().setLevelId(0l);

		testCustomerFieldsValidation(newCredentialRequest, Messages.LEVEL_ID_IS_NOT_VALID);

	}

	private void testCustomerFieldsValidation(NewCredentialRequest newCredentialRequest, String comparisonMessage)
			throws Exception {

		MvcResult result = mockMvc
				.perform(post(CREDENTIAL_PATH).contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(newCredentialRequest)))
				.andExpect(status().isBadRequest()).andReturn();

		ErrorResponse errorResponse = mapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);

		assertThat(errorResponse.getMessage(), equalTo(comparisonMessage));

	}

	@Test
	public void shouldEditCredential() throws Exception {

		HashMap<String, Object> param = new HashMap<>();
		param.put("loginId", 123);

		String path = UriComponentsBuilder.fromPath(EDIT_CREDENTIAL_PATH).buildAndExpand(param).toUriString();

		mockMvc.perform(put(path).content(mapper.writeValueAsString(editCredentialRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNoContent()).andDo(print());

		verify(credentialsService, times(1)).editCredential(anyLong(), any());

	}

	@Test
	public void shouldNotEditCredentialBecauseCredentialInfoRequired() throws Exception {

		editCredentialRequest.setEditCredentialDTO(null);

		HashMap<String, Object> param = new HashMap<>();
		param.put("loginId", 123);

		String path = UriComponentsBuilder.fromPath(EDIT_CREDENTIAL_PATH).buildAndExpand(param).toUriString();

		mockMvc.perform(put(path).content(mapper.writeValueAsString(editCredentialRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andDo(print());

		verify(credentialsService, never()).editCredential(anyLong(), any());

	}

	@Test
	public void shouldNotEditCredentialBecauseUsernameRequired() throws Exception {

		editCredentialRequest.getEditCredentialDTO().setUsername(null);

		HashMap<String, Object> param = new HashMap<>();
		param.put("loginId", 123);

		String path = UriComponentsBuilder.fromPath(EDIT_CREDENTIAL_PATH).buildAndExpand(param).toUriString();

		mockMvc.perform(put(path).content(mapper.writeValueAsString(editCredentialRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andDo(print());

		verify(credentialsService, never()).editCredential(anyLong(), any());

	}

	@Test
	public void shouldNotEditCredentialBecauseUsernameNotValid() throws Exception {

		editCredentialRequest.getEditCredentialDTO().setUsername("aaa");

		HashMap<String, Object> param = new HashMap<>();
		param.put("loginId", 123);

		String path = UriComponentsBuilder.fromPath(EDIT_CREDENTIAL_PATH).buildAndExpand(param).toUriString();

		mockMvc.perform(put(path).content(mapper.writeValueAsString(editCredentialRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andDo(print());

		verify(credentialsService, never()).editCredential(anyLong(), any());

	}

	@Test
	public void shouldNotEditCredentialBecausePasswordRequired() throws Exception {

		editCredentialRequest.getEditCredentialDTO().setPassword(null);

		HashMap<String, Object> param = new HashMap<>();
		param.put("loginId", 123);

		String path = UriComponentsBuilder.fromPath(EDIT_CREDENTIAL_PATH).buildAndExpand(param).toUriString();

		mockMvc.perform(put(path).content(mapper.writeValueAsString(editCredentialRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andDo(print());

		verify(credentialsService, never()).editCredential(anyLong(), any());

	}

	@Test
	public void shouldNotEditCredentialBecausePasswordNotValid() throws Exception {

		editCredentialRequest.getEditCredentialDTO().setPassword("as");

		HashMap<String, Object> param = new HashMap<>();
		param.put("loginId", 123);

		String path = UriComponentsBuilder.fromPath(EDIT_CREDENTIAL_PATH).buildAndExpand(param).toUriString();

		mockMvc.perform(put(path).content(mapper.writeValueAsString(editCredentialRequest))
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andDo(print());

		verify(credentialsService, never()).editCredential(anyLong(), any());

	}

	@Test
	public void shouldDeleteCredential() throws Exception {

		HashMap<String, Object> param = new HashMap<>();
		param.put("loginId", 123);

		String path = UriComponentsBuilder.fromPath(DELETE_CREDENTIAL_PATH).buildAndExpand(param).toUriString();

		mockMvc.perform(delete(path)).andExpect(status().isNoContent()).andDo(print());

		verify(credentialsService, times(1)).deleteCredential(anyLong());

	}

}

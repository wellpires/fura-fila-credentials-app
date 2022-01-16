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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.request.NewCredentialRequest;
import br.com.furafila.credentialsapp.response.CouriersResponse;
import br.com.furafila.credentialsapp.response.CredentialDuplicityResponse;
import br.com.furafila.credentialsapp.response.NewLoginResponse;
import br.com.furafila.credentialsapp.service.CredentialsService;
import br.com.furafila.credentialsapp.util.ReplaceCamelCase;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CredentialController.class)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class CredentialControllerTest {

	private static final String CREDENTIAL_URL = "/credential";
	private static final String CREDENTIAL__DUPLICITY_URL = CREDENTIAL_URL.concat("/id/{id}/username/{username}");
	private static final String LIST_COURIERS_URL = CREDENTIAL_URL.concat("/couriers");

	@MockBean
	private CredentialsService credentialsService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

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

		NewCredentialRequest newCredentialRequest = new NewCredentialRequest();
		String json = mapper.writeValueAsString(newCredentialRequest);

		MvcResult result = mockMvc.perform(post(CREDENTIAL_URL).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn();

		NewLoginResponse newLoginResponse = mapper.readValue(result.getResponse().getContentAsString(),
				NewLoginResponse.class);

		assertNotNull(newLoginResponse);
		assertThat(newLoginResponse.getId(), equalTo(id));

	}

	@Test
	public void shouldNotSaveCredentialBecauseExceptionIsThrown() throws Exception {

		doThrow(new RuntimeException("TESTE ERRO")).when(credentialsService).saveCredential(any());

		String json = mapper.writeValueAsString(new NewCredentialRequest());

		mockMvc.perform(post(CREDENTIAL_URL).contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isInternalServerError());

	}

}

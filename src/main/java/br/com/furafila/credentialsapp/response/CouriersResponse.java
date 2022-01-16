package br.com.furafila.credentialsapp.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.furafila.credentialsapp.dto.CourierDTO;

public class CouriersResponse {

	@JsonProperty("couriers")
	private List<CourierDTO> couriersDTO;

	public CouriersResponse() {
	}

	public CouriersResponse(List<CourierDTO> couriers) {
		couriersDTO = couriers;
	}

	public List<CourierDTO> getCouriersDTO() {
		return couriersDTO;
	}

	public void setCouriersDTO(List<CourierDTO> couriersDTO) {
		this.couriersDTO = couriersDTO;
	}

}

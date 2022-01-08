package br.com.furafila.credentialsapp.builder;

import br.com.furafila.credentialsapp.dto.CourierDTO;

public class CourierDTOBuilder {

	private Long id;
	private String username;
	private Boolean status;
	private Boolean deliveryAvailable;

	public CourierDTOBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public CourierDTOBuilder username(String username) {
		this.username = username;
		return this;
	}

	public CourierDTOBuilder status(Boolean status) {
		this.status = status;
		return this;
	}

	public CourierDTOBuilder deliveryAvailable(Boolean deliveryAvailable) {
		this.deliveryAvailable = deliveryAvailable;
		return this;
	}

	public CourierDTO build() {
		CourierDTO courierDTO = new CourierDTO();
		courierDTO.setId(id);
		courierDTO.setUsername(username);
		courierDTO.setStatus(status);
		courierDTO.setDeliveryAvailable(deliveryAvailable);

		return courierDTO;
	}

}

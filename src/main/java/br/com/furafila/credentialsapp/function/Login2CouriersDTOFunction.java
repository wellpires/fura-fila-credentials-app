package br.com.furafila.credentialsapp.function;

import java.util.function.Function;

import br.com.furafila.credentialsapp.builder.CourierDTOBuilder;
import br.com.furafila.credentialsapp.dto.CourierDTO;
import br.com.furafila.credentialsapp.model.Login;

public class Login2CouriersDTOFunction implements Function<Login, CourierDTO> {

	@Override
	public CourierDTO apply(Login login) {
		return new CourierDTOBuilder().id(login.getId()).username(login.getUsername()).status(login.getStatus())
				.deliveryAvailable(login.getDeliveryAvailable()).build();
	}

}

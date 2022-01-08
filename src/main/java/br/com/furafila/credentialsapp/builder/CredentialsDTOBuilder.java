package br.com.furafila.credentialsapp.builder;

import br.com.furafila.credentialsapp.dto.CredentialDTO;

public class CredentialsDTOBuilder {

	private Long idLogin;
	private String username;
	private String password;
	private Long idLevel;
	private String levelInitials;
	private String level;

	public CredentialsDTOBuilder idLogin(Long idLogin) {
		this.idLogin = idLogin;
		return this;
	}

	public CredentialsDTOBuilder username(String username) {
		this.username = username;
		return this;
	}

	public CredentialsDTOBuilder password(String password) {
		this.password = password;
		return this;
	}

	public CredentialsDTOBuilder idLevel(Long idLevel) {
		this.idLevel = idLevel;
		return this;
	}

	public CredentialsDTOBuilder levelInitials(String levelInitials) {
		this.levelInitials = levelInitials;
		return this;
	}

	public CredentialsDTOBuilder level(String level) {
		this.level = level;
		return this;
	}

	public CredentialDTO build() {
		CredentialDTO credentialsDTO = new CredentialDTO();
		credentialsDTO.setId(idLogin);
		credentialsDTO.setUsername(username);
		credentialsDTO.setPassword(password);
		credentialsDTO.setLevelId(idLevel);
		credentialsDTO.setLevel(level);
		credentialsDTO.setInitials(levelInitials);

		return credentialsDTO;
	}

}

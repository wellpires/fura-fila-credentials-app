package br.com.furafila.credentialsapp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.furafila.credentialsapp.model.converter.Bit2BooleanConverter;

@Entity
@Table(name = "login")
public class Login implements Serializable {

	private static final long serialVersionUID = -2802735816772511916L;

	@Id
	@Column(name = "id_login", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "usuario")
	private String username;

	@Column(name = "senha")
	private String password;

	@Column(name = "status", columnDefinition = "int4")
	@Convert(converter = Bit2BooleanConverter.class)
	private Integer status;

	@Column(name = "disponivel_entrega")
	private Integer deliveryAvailable;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_permissao_fk", referencedColumnName = "id_permissao", columnDefinition = "int4")
	private Permissao permissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeliveryAvailable() {
		return deliveryAvailable;
	}

	public void setDeliveryAvailable(Integer deliveryAvailable) {
		this.deliveryAvailable = deliveryAvailable;
	}

	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

}

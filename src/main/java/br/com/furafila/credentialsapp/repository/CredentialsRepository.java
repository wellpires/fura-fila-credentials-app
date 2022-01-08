package br.com.furafila.credentialsapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.furafila.credentialsapp.model.Login;

public interface CredentialsRepository extends JpaRepository<Login, Long> {

	@Query("SELECT l, p FROM Login l INNER JOIN l.permissao p where l.username = :username and l.password = :password")
	public Optional<Login> findCredentials(@Param("username") String username, @Param("password") String password);

	public List<Login> findLoginByUsername(String username);

	@Query("SELECT l, p FROM Login l INNER JOIN l.permissao p WHERE p.initials = :levelInitial")
	public List<Login> findAllCouriers(String levelInitial);

}

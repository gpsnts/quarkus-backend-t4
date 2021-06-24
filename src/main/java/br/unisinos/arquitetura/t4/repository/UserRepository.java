package br.unisinos.arquitetura.t4.repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import br.unisinos.arquitetura.t4.entity.Role;
import br.unisinos.arquitetura.t4.entity.User;
import br.unisinos.arquitetura.t4.security.ArgonHashing;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
	public User findByUsername(String username) {
		return find("username", username).firstResult();
	}

	@Transactional
	public void creatUser(String username, String password, Set<Role> roles) {
		User user = User.builder()
			.username(username)
			.password(ArgonHashing.passwordHashing(password))
			.roles(
				Optional.of(roles)
								.orElse(Stream.of(Role.USER)
								.collect(Collectors.toSet()))
			)
		.build();
		
		user.persist();
	}
}

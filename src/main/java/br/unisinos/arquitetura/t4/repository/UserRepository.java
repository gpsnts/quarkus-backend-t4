package br.unisinos.arquitetura.t4.repository;

import javax.enterprise.context.ApplicationScoped;

import br.unisinos.arquitetura.t4.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
	
	public User findByUsername(String username) {
		return find("username", username).firstResult();
	}
}

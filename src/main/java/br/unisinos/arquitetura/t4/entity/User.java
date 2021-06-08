package br.unisinos.arquitetura.t4.entity;

import java.util.Set;

import javax.management.relation.Role;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class User extends PanacheEntity 
{
	private String username, password;
	private Set<Role> roles;
	
	// Add service layer
	public static User findByUsername(String username) {
		return find("username", username).firstResult();
	}
}

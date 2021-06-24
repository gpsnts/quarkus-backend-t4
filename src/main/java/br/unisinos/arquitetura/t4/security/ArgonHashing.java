package br.unisinos.arquitetura.t4.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArgonHashing {
	private static final Argon2 factory = Argon2Factory.create();
	public static Argon2 getInstance() {
		return factory;
	}
	public static String passwordHashing(String password) {
		return factory.hash(10, 65536, 1, password.toCharArray());
	}
}

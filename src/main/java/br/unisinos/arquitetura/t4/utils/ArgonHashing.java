package br.unisinos.arquitetura.t4.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArgonHashing {
	private static Argon2 factory = Argon2Factory.create();

	public static Argon2 getInstance() {
		return factory;
	}

	public static String passwordHashing(String password) {
		return factory.hash(10, 65536, 1, password.toCharArray());
	}

	public static boolean verifyPassword(String inputPassword, String storedPassword) {
		log.info("inputPassword --> ", inputPassword);
		log.info("inputPassword.length() --> ", inputPassword.length());
		log.info("passwordHashing(inputPassword) ---- > ", passwordHashing(inputPassword).toString());
		log.info("Result -----> {}", factory.verify(passwordHashing(inputPassword), storedPassword.toCharArray()));
		return factory.verify(passwordHashing(inputPassword), storedPassword.toCharArray());
	}
}

package br.unisinos.arquitetura.t4.utils;

public class Validators {
	public static boolean validateInvalidString(String st) {
		return st.isEmpty() || st.isBlank() || st == null;
	}

	public static boolean validPasswordLength(String pswd) {
		return pswd.length() < 8;
	}
}

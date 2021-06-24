package br.unisinos.arquitetura.t4.security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;

import br.unisinos.arquitetura.t4.config.SecurityConfig;

@RequestScoped
public class EncodingHandler {
	public String encode(CharSequence seq) {
		SecurityConfig config = new SecurityConfig();
		
		try {
			Map<String, String> securityConfig = config.getSecurityPropsValues();

			PBEKeySpec spec = new PBEKeySpec(
				seq.toString().toCharArray(),
				securityConfig.get("secret").toString().getBytes(),
				Integer.parseInt(securityConfig.get("iteration").toString()),
				Integer.parseInt(securityConfig.get("lenght").toString())
			);
			
			byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
												.generateSecret(spec)
												.getEncoded();
			
			return Base64.getEncoder().encodeToString(result);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
}

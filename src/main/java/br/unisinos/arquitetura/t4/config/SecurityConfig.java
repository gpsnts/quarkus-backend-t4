package br.unisinos.arquitetura.t4.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SecurityConfig {
	private InputStream inputStream;

	public Map<String, String> getSecurityPropsValues() throws IOException {
		Map<String, String> securityProps = new HashMap<>();

		try {
			Properties props = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream("security.properties");

			if (inputStream != null) props.load(inputStream);
			else throw new FileNotFoundException("Can not found selected prop file");

			securityProps.put("secret", props.getProperty("secret"));
			securityProps.put("iteration", props.getProperty("iteration"));
			securityProps.put("lenght", props.getProperty("lenght"));

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			inputStream.close();
		}

		return securityProps;
	}
}

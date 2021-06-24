package br.unisinos.arquitetura.t4.dto.response.standard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTResponse {
		String access_token;
		String exp;
		String parsed;
		String foo;
}

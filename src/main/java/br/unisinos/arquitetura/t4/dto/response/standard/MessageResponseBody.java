package br.unisinos.arquitetura.t4.dto.response.standard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseBody {
	String message;
	Object[] body;
	StackTraceElement[] stacktrace;
}

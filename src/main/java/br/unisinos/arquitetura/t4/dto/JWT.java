package br.unisinos.arquitetura.t4.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JWT {
		String iss, sub, username, iat, exp;
//    Set<Role> roles;
}

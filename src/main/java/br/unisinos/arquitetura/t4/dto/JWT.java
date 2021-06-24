package br.unisinos.arquitetura.t4.dto;

import br.unisinos.arquitetura.t4.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JWT {
    String iss, sub, username, iat, exp;
//    Set<Role> roles;
}

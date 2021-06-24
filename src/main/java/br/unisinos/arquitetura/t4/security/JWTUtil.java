package br.unisinos.arquitetura.t4.security;

import br.unisinos.arquitetura.t4.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

public class JWTUtil {
    public static String getExpirationFromJWT(String jwt) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(decodeJWT(jwt).getExpiration());
    }

    public static DefaultClaims decodeJWT(String jwt) {
        DefaultClaims body = (DefaultClaims) Jwts.parserBuilder()
            .setSigningKey(
                new SecretKeySpec(
                    "tkXRICPxD2xezlk9oExFdUg8T5sCiIpkT".getBytes(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.getJcaName()
                )
            )
            .build().parse(jwt).getBody();
        return body;
    }

    public static String createToken(String username, Set<Role> roles, Long duration, String issuer) {
        return Jwts.builder()
            .setIssuer(issuer)
            .setSubject(username)
                .claim("username", username)
                .claim("roles", roles)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusSeconds(600L)))
            .signWith(
                new SecretKeySpec(
                    "tkXRICPxD2xezlk9oExFdUg8T5sCiIpkT".getBytes(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.getJcaName()
                )
            )
        .compact();
    }
}

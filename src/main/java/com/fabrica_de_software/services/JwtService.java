package com.fabrica_de_software.services;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.config.UserDetailsImpl;
import com.fabrica_de_software.entities.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	// mínimo 32 caracteres
	private static final String SECRET = "minha_chave_super_secreta_123456789";

	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	public String gerarToken(UserDetailsImpl userDetails) {
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return Jwts.builder().setSubject(userDetails.getUsername()).claim("user_id", userDetails.getUsuarioId())
				.claim("roles", roles).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public Claims validarToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public Long getUserId(String token) {
		Claims claims = validarToken(token);
		return claims.get("user_id", Long.class);
	}

	public List<Role> getRoles(String token) {
		Claims claims = validarToken(token);
		return claims.get("roles", List.class);
	}

	public String getUsername(String token) {
		Claims claims = validarToken(token);
		return claims.getSubject();
	}
}

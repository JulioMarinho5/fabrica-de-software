package com.fabrica_de_software.config;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.repositories.UsuarioRepository;
import com.fabrica_de_software.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UsuarioRepository usuarioRepository;

	public JwtFilter(JwtService jwtService, UsuarioRepository usuarioRepository) {
		this.jwtService = jwtService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, java.io.IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				Claims claims = jwtService.validarToken(token);
				Long userId = claims.get("user_id", Long.class);
				String role = claims.get("role", String.class); // Ou List<Role>
				String email = claims.getSubject();
				Usuario u = usuarioRepository.findByEmail(email)
						.orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
				UserDetailsImpl userDetails = new UserDetailsImpl(u);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, List.of(new SimpleGrantedAuthority(role)));
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				request.setAttribute("user_id", userId);
			} catch (Exception e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				try {
					response.getWriter().write("""
							 		{
							   	"erro": "Token inválido ou expirado"
									}
							""");
				} catch (java.io.IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				return;
			}
		}
		filterChain.doFilter(request, response);
	}
}

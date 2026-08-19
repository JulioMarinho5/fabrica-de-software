package com.fabrica_de_software.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.repositories.UsuarioRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
	private final UsuarioRepository usuarioRepository;

	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario u = usuarioRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Professor não encontrado!"));

		return new UserDetailsImpl(u);

	}

}

package com.fabrica_de_software.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.config.UserDetailsImpl;
import com.fabrica_de_software.dtos.CadastroAdministradorRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.entities.Administrador;
import com.fabrica_de_software.entities.Role;
import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.enums.RoleEnum;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.AdministradorJaCadastradoException;
import com.fabrica_de_software.exceptions.RoleNaoEncontradoException;
import com.fabrica_de_software.repositories.AdministradorRepository;
import com.fabrica_de_software.repositories.RoleRepository;
import com.fabrica_de_software.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class AdministradorService {
	private final AdministradorRepository administradorRepository;
	private final UsuarioRepository usuarioRepository;
	private final RoleRepository roleRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final GeradorDeRaService geradorRa;
	private final JwtService jwtService;

	public AdministradorService(AdministradorRepository administradorRepository, UsuarioRepository usuarioRepository,
			RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
			GeradorDeRaService geradorRa, JwtService jwtService) {
		this.administradorRepository = administradorRepository;
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.geradorRa = geradorRa;
		this.jwtService = jwtService;
	}

	@Transactional
	public MensagemResponseDto cadastrarAdmin(CadastroAdministradorRequestDto dto) {
		Optional<Usuario> opUsuario = usuarioRepository.findByEmail(dto.email());
		Role roleNovoAdmin = roleRepository.findByRole(RoleEnum.ROLE_ADMIN)
				.orElseThrow(() -> new RoleNaoEncontradoException("Role não encontrado na base de dados"));
		if (opUsuario.isEmpty()) {
			boolean raOk = false;
			String ra = null;
			while (!raOk) {
				ra = geradorRa.gerarRa();
				raOk = usuarioRepository.existsByRa(ra) ? false : true;
			}
			Usuario u = new Usuario(dto.email(), passwordEncoder.encode(ra), dto.nome(), dto.telefone(), ra);
			u.setRoles(List.of(roleNovoAdmin));
			Usuario usuario = usuarioRepository.save(u);
			Administrador adm = new Administrador(usuario, Status.ATIVO);
			administradorRepository.save(adm);
		} else {
			Optional<Administrador> opAdm = administradorRepository.findByEmail(dto.email());
			if (opAdm.isPresent()) {
				throw new AdministradorJaCadastradoException("Administrador já cadastrado!");
			}
			Usuario usuario = opUsuario.get();
			usuario.getRoles().add(roleNovoAdmin);
			usuarioRepository.save(usuario);
			Administrador adm = new Administrador(usuario, Status.ATIVO);
			administradorRepository.save(adm);
		}
		return new MensagemResponseDto("Administrador cadastrado com sucesso!", LocalDateTime.now());
	}

	public LoginResponseDto loginAdmin(LoginRequestDto dto) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		return new LoginResponseDto(jwtService.gerarToken(userDetails), usuario.getNome(), usuario.getEmail(),
				usuario.getTelefone(), usuario.getRa());
	}

}

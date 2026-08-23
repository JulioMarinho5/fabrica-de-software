package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.config.UserDetailsImpl;
import com.fabrica_de_software.dtos.CadastroProfessorRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.dtos.ProfessorResponseDto;
import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.entities.Role;
import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.enums.RoleEnum;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.ProfessorJaCadastradoException;
import com.fabrica_de_software.exceptions.RoleNaoEncontradoException;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.RoleRepository;
import com.fabrica_de_software.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfessorService {
	private final ProfessorRepository professorRepository;
	private final ProfessorProducer professorProducer;
	private final UsuarioRepository usuarioRepository;
	private final RoleRepository roleRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final GeradorDeRaService geradorRa;

	public ProfessorService(ProfessorRepository professorRepository, ProfessorProducer professorProducer,
			UsuarioRepository usuarioRepository, RoleRepository roleRepository,
			AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService,
			GeradorDeRaService geradorRa) {
		this.professorRepository = professorRepository;
		this.professorProducer = professorProducer;
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.geradorRa = geradorRa;
	}

	@Transactional
	public MensagemResponseDto cadastrarProfessor(CadastroProfessorRequestDto dto) {
		Optional<Usuario> opUsuario = usuarioRepository.findByEmail(dto.email());
		Role roleNovoProf = roleRepository.findByRole(RoleEnum.ROLE_PROFESSOR)
				.orElseThrow(() -> new RoleNaoEncontradoException("Role não encontrado na base de dados"));
		String ra = null;
		if (opUsuario.isEmpty()) {
			boolean raOk = false;
			while (!raOk) {
				ra = geradorRa.gerarRa();
				raOk = usuarioRepository.existsByRa(ra) ? false : true;
			}
			Usuario u = new Usuario(dto.email(), passwordEncoder.encode(ra), dto.nome(), dto.telefone(), ra);
			u.setRoles(List.of(roleNovoProf));
			Usuario usuario = usuarioRepository.save(u);
			Professor professor = new Professor(dto.escola(), LocalDate.now(), usuario, Status.ATIVO);
			professorRepository.save(professor);
		} else {
			Optional<Professor> opAdm = professorRepository.findByEmail(dto.email());
			if (opAdm.isPresent()) {
				throw new ProfessorJaCadastradoException("Professor já cadastrado!");
			}
			Usuario usuario = opUsuario.get();
			usuario.getRoles().add(roleNovoProf);
			usuarioRepository.save(usuario);
			Professor professor = new Professor(dto.escola(), LocalDate.now(), usuario, Status.ATIVO);
			professorRepository.save(professor);
		}
		professorProducer.enviarEmailCadastro(dto.email(), ra);
		return new MensagemResponseDto("Professor cadastrado com sucesso!", LocalDateTime.now());

	}

	public LoginResponseDto loginProfessor(LoginRequestDto dto) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		return new LoginResponseDto(jwtService.gerarToken(userDetails), usuario.getNome(), usuario.getEmail(),
				usuario.getTelefone(), usuario.getRa());
	}

	public List<ProfessorResponseDto> listarProfessores() {
		return professorRepository.findAll().stream().map(p -> new ProfessorResponseDto(p, p.getUsuario())).toList();
	}

}

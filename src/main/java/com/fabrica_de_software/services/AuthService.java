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
import com.fabrica_de_software.dtos.CadastroAdministradorRequestDto;
import com.fabrica_de_software.dtos.CadastroAlunoRequestDto;
import com.fabrica_de_software.dtos.CadastroProfessorRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.entities.Administrador;
import com.fabrica_de_software.entities.Aluno;
import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.entities.Role;
import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.enums.RoleEnum;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.AdministradorJaCadastradoException;
import com.fabrica_de_software.exceptions.AlunoJaCadastradoEmGrupoException;
import com.fabrica_de_software.exceptions.LoginInvalidoException;
import com.fabrica_de_software.exceptions.ProfessorJaCadastradoException;
import com.fabrica_de_software.exceptions.RoleNaoEncontradoException;
import com.fabrica_de_software.notificacoes.AdministradorProducer;
import com.fabrica_de_software.notificacoes.AlunoProducer;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.AdministradorRepository;
import com.fabrica_de_software.repositories.AlunoRepository;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.RoleRepository;
import com.fabrica_de_software.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
	private final AdministradorRepository administradorRepository;
	private final UsuarioRepository usuarioRepository;
	private final RoleRepository roleRepository;
	private final ProfessorRepository professorRepository;
	private final AlunoRepository alunoRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final GeradorDeRaService geradorRa;
	private final JwtService jwtService;
	private final AdministradorProducer administradorProducer;
	private final ProfessorProducer professorProducer;
	private final AlunoProducer alunoProducer;

	public AuthService(AdministradorRepository administradorRepository, UsuarioRepository usuarioRepository,
			RoleRepository roleRepository, ProfessorRepository professorRepository, AlunoRepository alunoRepository,
			AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, GeradorDeRaService geradorRa,
			JwtService jwtService, AdministradorProducer administradorProducer, ProfessorProducer professorProducer,
			AlunoProducer alunoProducer) {
		this.administradorRepository = administradorRepository;
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.professorRepository = professorRepository;
		this.alunoRepository = alunoRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.geradorRa = geradorRa;
		this.jwtService = jwtService;
		this.administradorProducer = administradorProducer;
		this.professorProducer = professorProducer;
		this.alunoProducer = alunoProducer;
	}

	public LoginResponseDto login(LoginRequestDto dto) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		boolean possuiRole = usuario.getRoles().stream()
				.anyMatch(role -> role.getRole().toString().equals(dto.portal()));
		if (!possuiRole) {
			throw new LoginInvalidoException("Login Inválido! Tente acessar outro portal.");
		}
		return new LoginResponseDto(jwtService.gerarToken(userDetails), usuario.getNome(), usuario.getEmail(),
				usuario.getTelefone(), usuario.getRa());
	}

	@Transactional
	public MensagemResponseDto cadastrarAdmin(CadastroAdministradorRequestDto dto) {
		Optional<Usuario> opUsuario = usuarioRepository.findByEmail(dto.email());
		Role roleNovoAdmin = roleRepository.findByRole(RoleEnum.ROLE_ADMIN)
				.orElseThrow(() -> new RoleNaoEncontradoException("Role não encontrado na base de dados"));
		String ra = null;
		if (opUsuario.isEmpty()) {
			boolean raOk = false;
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
			Usuario usuario = opUsuario.get();
			Optional<Administrador> opAdm = administradorRepository.findByUsuarioId(usuario.getId());
			if (opAdm.isPresent()) {
				throw new AdministradorJaCadastradoException("Administrador já cadastrado!");
			}
			ra = usuario.getRa();
			usuario.getRoles().add(roleNovoAdmin);
			usuarioRepository.save(usuario);
			Administrador adm = new Administrador(usuario, Status.ATIVO);
			administradorRepository.save(adm);
		}
		administradorProducer.enviarEmailCadastro(dto.email(), ra);
		return new MensagemResponseDto("Administrador cadastrado com sucesso!", LocalDateTime.now());
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
			Usuario usuario = opUsuario.get();
			Optional<Professor> opAdm = professorRepository.findByUsuarioId(usuario.getId());
			if (opAdm.isPresent()) {
				throw new ProfessorJaCadastradoException("Professor já cadastrado!");
			}
			ra = usuario.getRa();
			usuario.getRoles().add(roleNovoProf);
			usuarioRepository.save(usuario);
			Professor professor = new Professor(dto.escola(), LocalDate.now(), usuario, Status.ATIVO);
			professorRepository.save(professor);
		}
		professorProducer.enviarEmailCadastro(dto.email(), ra);
		return new MensagemResponseDto("Professor cadastrado com sucesso!", LocalDateTime.now());

	}

	@Transactional
	public MensagemResponseDto cadastrarAluno(CadastroAlunoRequestDto dto) {
		Optional<Usuario> opUsuario = usuarioRepository.findByEmail(dto.email());
		Role roleNovoAluno = roleRepository.findByRole(RoleEnum.ROLE_ALUNO)
				.orElseThrow(() -> new RoleNaoEncontradoException("Role não encontrado na base de dados"));
		String ra = null;
		if (opUsuario.isEmpty()) {
			boolean raOk = false;
			while (!raOk) {
				ra = geradorRa.gerarRa();
				raOk = usuarioRepository.existsByRa(ra) ? false : true;
			}
			Usuario u = new Usuario(dto.email(), passwordEncoder.encode(ra), dto.nome(), dto.telefone(), ra);
			u.setRoles(List.of(roleNovoAluno));
			Usuario usuario = usuarioRepository.save(u);
			Aluno aluno = new Aluno(dto.curso(), dto.turno(), dto.horasSemanais(), dto.githubUrl(), dto.linkedinUrl(),
					dto.dataSelecao(), LocalDate.now(), null, Status.ATIVO, usuario);
			alunoRepository.save(aluno);
		} else {
			Usuario usuario = opUsuario.get();
			Optional<Aluno> opAluno = alunoRepository.findByUsuarioId(usuario.getId());
			if (opAluno.isPresent()) {
				throw new AlunoJaCadastradoEmGrupoException("Aluno já cadastrado!");
			}
			ra = usuario.getRa();
			usuario.getRoles().add(roleNovoAluno);
			usuarioRepository.save(usuario);
			Aluno aluno = new Aluno(dto.curso(), dto.turno(), dto.horasSemanais(), dto.githubUrl().toLowerCase().trim(),
					dto.linkedinUrl().toLowerCase().trim(), dto.dataSelecao(), LocalDate.now(), null, Status.ATIVO,
					usuario);
			alunoRepository.save(aluno);
		}
		alunoProducer.enviarEmailCadastro(dto.email(), ra);
		return new MensagemResponseDto("Aluno cadastrado com sucesso!", LocalDateTime.now());

	}

}

package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fabrica_de_software.dtos.CadastroProfessorRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.dtos.ProfessorResponseDto;
import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.ProfessorJaCadastradoException;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.SenhaIncorretaException;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.RoleRepository;
import com.fabrica_de_software.repositories.UsuarioRepository;

@Service
public class ProfessorService {
	private ProfessorRepository professorRepository;
	private ProfessorProducer professorProducer;
	private final UsuarioRepository usuarioRepository;
	private final RoleRepository roleRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public ProfessorService(ProfessorRepository professorRepository, ProfessorProducer professorProducer,
			UsuarioRepository usuarioRepository, RoleRepository roleRepository,
			AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.professorRepository = professorRepository;
		this.professorProducer = professorProducer;
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public MensagemResponseDto cadastrarProfessor(CadastroProfessorRequestDto dto) {
		Optional<Professor> op = professorRepository.findByEmail(dto.email().toLowerCase());
		if (op.isPresent()) {
			throw new ProfessorJaCadastradoException("Um Professor com esse Email já está cadastrado");
		}
		boolean isOk = false;
		String ra = null;
		while (!isOk) {
			ra = geradorRa.gerarRaProfessor();
			if (professorRepository.existsByRa(ra)) {
				isOk = false;
			} else {
				isOk = true;
			}
		}
		Professor professor = new Professor(ra, dto.nome(), dto.email().toLowerCase(), dto.telefone(), dto.escola(),
				LocalDate.now(), Status.ATIVO, passwordEncoder.encode(ra));
		professorRepository.save(professor);
		professorProducer.enviarEmailCadastro(professor.getEmail(), ra);
		return new MensagemResponseDto("Professor cadastrado com sucesso!", LocalDateTime.now());

	}

	public LoginResponseDto loginProfessor(LoginRequestDto dto) {
		return professorRepository.findByEmail(dto.email().toLowerCase()).map(p -> {
			if (!passwordEncoder.matches(dto.getSenha(), p.getSenha())) {
				throw new SenhaIncorretaException("Senha incorreta! Tente novamente");
			}
			String token = jwtService.gerarToken(p);
			return new LoginResponseDto(token, p.getNome(), p.getEmail(), p.getTelefone());
		}).orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
	}

	public List<ProfessorResponseDto> listarProfessores() {
		return professorRepository.findAll().stream().map(p -> new ProfessorResponseDto(p, p.getUsuario())).toList();
	}

}

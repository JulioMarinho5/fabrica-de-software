package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.CadastroAlunoRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.entities.Aluno;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.AlunoJaCadastradoException;
import com.fabrica_de_software.exceptions.GithubUrlJaExistenteException;
import com.fabrica_de_software.exceptions.LinkedinUrlJaExistenteException;
import com.fabrica_de_software.repositories.AlunoRepository;
import com.fabrica_de_software.repositories.RoleRepository;
import com.fabrica_de_software.repositories.UsuarioRepository;

@Service
public class AlunoService {
	private AlunoRepository alunoRepository;
	private GeradorDeRaService geradorRa;
	private final UsuarioRepository usuarioRepository;
	private final RoleRepository roleRepository;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public AlunoService(AlunoRepository alunoRepository, GeradorDeRaService geradorRa,
			UsuarioRepository usuarioRepository, RoleRepository roleRepository,
			AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.alunoRepository = alunoRepository;
		this.geradorRa = geradorRa;
		this.usuarioRepository = usuarioRepository;
		this.roleRepository = roleRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public MensagemResponseDto cadastrarAluno(CadastroAlunoRequestDto dto) {
		Optional<Aluno> op = alunoRepository.findByEmail(dto.email().toLowerCase());
		if (op.isPresent()) {
			throw new AlunoJaCadastradoException("Um aluno com esse Email já está cadastrado");
		}
		if (alunoRepository.existsByGithubUrl(dto.githubUrl().toLowerCase().trim())) {
			throw new GithubUrlJaExistenteException("Essa URL do Github já está em uso!");
		}
		if (alunoRepository.existsByLinkedinUrl(dto.linkedinUrl().toLowerCase().trim())) {
			throw new LinkedinUrlJaExistenteException("Essa URL do Linkedin já está em uso!");
		}
		boolean isOk = false;
		String ra = null;
		while (!isOk) {
			ra = geradorRa.gerarRaAluno();
			if (alunoRepository.existsByRa(ra)) {
				isOk = false;
			} else {
				isOk = true;
			}
		}
		Aluno aluno = new Aluno(ra, dto.nome(), dto.email().toLowerCase(), dto.telefone(), dto.curso(), dto.turno(),
				dto.horasSemanais(), dto.githubUrl().toLowerCase().trim(), dto.linkedinUrl().toLowerCase().trim(),
				dto.dataSelecao(), LocalDate.now(), null, Status.ATIVO);
		alunoRepository.save(aluno);
		return new MensagemResponseDto("Aluno cadastrado com sucesso!", LocalDateTime.now());
	}

	public LoginResponseDto loginAluno(LoginRequestDto dto) {

	}

	public List<AlunoResponseDto> listarAlunosDisponiveis() {
		List<Aluno> alunos = alunoRepository.findByGrupoIdIsNull();
		return alunos.stream().map(a -> new AlunoResponseDto(a)).toList();
	}

	public List<AlunoResponseDto> listarTodosOsAlunos() {
		return alunoRepository.findAll().stream().map(a -> new AlunoResponseDto(a)).toList();
	}

}

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
import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.CadastroAlunoRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.entities.Aluno;
import com.fabrica_de_software.entities.Role;
import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.enums.RoleEnum;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.AlunoJaCadastradoEmGrupoException;
import com.fabrica_de_software.exceptions.RoleNaoEncontradoException;
import com.fabrica_de_software.repositories.AlunoRepository;
import com.fabrica_de_software.repositories.RoleRepository;
import com.fabrica_de_software.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class AlunoService {
	private final AlunoRepository alunoRepository;
	private final GeradorDeRaService geradorRa;
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

	@Transactional
	public MensagemResponseDto cadastrarAluno(CadastroAlunoRequestDto dto) {
		Optional<Usuario> opUsuario = usuarioRepository.findByEmail(dto.email());
		Role roleNovoAluno = roleRepository.findByRole(RoleEnum.ROLE_ALUNO)
				.orElseThrow(() -> new RoleNaoEncontradoException("Role não encontrado na base de dados"));
		if (opUsuario.isEmpty()) {
			boolean raOk = false;
			String ra = null;
			while (!raOk) {
				ra = geradorRa.gerarRa();
				raOk = usuarioRepository.existsByRa(ra) ? false : true;
			}
			Usuario u = new Usuario(dto.email(), passwordEncoder.encode(ra), dto.nome(), dto.telefone(), ra);
			u.setRoles(List.of(roleNovoAluno));
			Usuario usuario = usuarioRepository.save(u);
			Aluno aluno = new Aluno(dto.curso(), dto.turno(), dto.horasSemanais(), dto.githubUrl().toLowerCase().trim(),
					dto.linkedinUrl().toLowerCase().trim(), dto.dataSelecao(), LocalDate.now(), null, Status.ATIVO,
					usuario);
			alunoRepository.save(aluno);
		} else {
			Optional<Aluno> opAluno = alunoRepository.findByEmail(dto.email());
			if (opAluno.isPresent()) {
				throw new AlunoJaCadastradoEmGrupoException("Aluno já cadastrado!");
			}
			Usuario usuario = opUsuario.get();
			usuario.getRoles().add(roleNovoAluno);
			usuarioRepository.save(usuario);
			Aluno aluno = new Aluno(dto.curso(), dto.turno(), dto.horasSemanais(), dto.githubUrl().toLowerCase().trim(),
					dto.linkedinUrl().toLowerCase().trim(), dto.dataSelecao(), LocalDate.now(), null, Status.ATIVO,
					usuario);
			alunoRepository.save(aluno);
		}
		return new MensagemResponseDto("Aluno cadastrado com sucesso!", LocalDateTime.now());

	}

	public LoginResponseDto loginAluno(LoginRequestDto dto) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.senha()));
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		return new LoginResponseDto(jwtService.gerarToken(userDetails), usuario.getNome(), usuario.getEmail(),
				usuario.getTelefone(), usuario.getRa());
	}

	public List<AlunoResponseDto> listarAlunosDisponiveis() {
		List<Aluno> alunos = alunoRepository.findByGrupoIdIsNull();
		return alunos.stream().map(a -> new AlunoResponseDto(a, a.getUsuario())).toList();
	}

	public List<AlunoResponseDto> listarTodosOsAlunos() {
		return alunoRepository.findAll().stream().map(a -> new AlunoResponseDto(a, a.getUsuario())).toList();
	}

}

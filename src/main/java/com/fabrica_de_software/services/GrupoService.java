package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.CadastroGrupoRequestDto;
import com.fabrica_de_software.dtos.GrupoResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.dtos.ProfessorResponseDto;
import com.fabrica_de_software.dtos.ProjetoResponseDto;
import com.fabrica_de_software.entities.Aluno;
import com.fabrica_de_software.entities.Grupo;
import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.entities.Projeto;
import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.AlunoJaCadastradoEmGrupoException;
import com.fabrica_de_software.exceptions.AlunoNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProjetoNaoEncontradoException;
import com.fabrica_de_software.exceptions.UsuarioNaoEncontradoException;
import com.fabrica_de_software.notificacoes.AlunoProducer;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.AlunoRepository;
import com.fabrica_de_software.repositories.GrupoRepository;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.ProjetoRepository;
import com.fabrica_de_software.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class GrupoService {
	private final GrupoRepository grupoRepository;
	private final ProjetoRepository projetoRepository;
	private final ProfessorRepository professorRepository;
	private final AlunoRepository alunoRepository;
	private final ProfessorProducer professorProducer;
	private final AlunoProducer alunoProducer;
	private final UsuarioRepository usuarioRepository;

	public GrupoService(GrupoRepository grupoRepository, ProjetoRepository projetoRepository,
			ProfessorRepository professorRepository, AlunoRepository alunoRepository,
			ProfessorProducer professorProducer, AlunoProducer alunoProducer, UsuarioRepository usuarioRepository) {
		this.grupoRepository = grupoRepository;
		this.projetoRepository = projetoRepository;
		this.professorRepository = professorRepository;
		this.alunoRepository = alunoRepository;
		this.professorProducer = professorProducer;
		this.alunoProducer = alunoProducer;
		this.usuarioRepository = usuarioRepository;
	}

	public MensagemResponseDto criarNovoGrupo(CadastroGrupoRequestDto dto) {
		Projeto projeto = projetoRepository.findById(dto.projetoId())
				.orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado!"));
		Usuario usuario = usuarioRepository.findByEmail(dto.professorCoordenadorEmail().toLowerCase())
				.orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado!"));
		Professor professor = professorRepository.findByUsuarioId(usuario.getId())
				.orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
		List<Aluno> alunosDoGrupo = alunoRepository.findAllById(dto.alunosIds());
		if (alunosDoGrupo.size() != dto.alunosIds().size()) {
			throw new AlunoNaoEncontradoException("Um ou mais alunos informados não foram encontrados!");
		}
		for (Aluno a : alunosDoGrupo) {
			if (a.getGrupo() != null) {
				throw new AlunoJaCadastradoEmGrupoException(
						"O aluno " + a.getUsuario().getNome() + " já faz parte de outro grupo!");
			}
		}
		Grupo g = new Grupo(projeto, professor, LocalDate.now(), Status.ATIVO);
		Grupo grupo = grupoRepository.save(g);
		List<AlunoResponseDto> alunosDto = alunosDoGrupo.stream().map(a -> new AlunoResponseDto(a, a.getUsuario()))
				.toList();
		for (Aluno a : alunosDoGrupo) {
			a.setGrupo(grupo);
			alunoProducer.enviarEmailGrupo(a.getUsuario().getEmail(), alunosDto);
		}
		alunoRepository.saveAll(alunosDoGrupo);
		projeto.setTemGrupo(true);
		projetoRepository.save(projeto);
		professorProducer.enviarEmailGrupo(usuario.getEmail(), alunosDto);
		return new MensagemResponseDto("Grupo criado com sucesso!", LocalDateTime.now());
	}

	@Transactional
	public List<GrupoResponseDto> listarGrupos() {
		return grupoRepository.findAllCompleto().stream()
				.map(grupo -> new GrupoResponseDto(grupo.getId(), new ProjetoResponseDto(grupo.getProjeto()),
						new ProfessorResponseDto(grupo.getProfessor(), grupo.getProfessor().getUsuario()),
						grupo.getAlunos().stream().map(a -> new AlunoResponseDto(a, a.getUsuario())).toList()))
				.toList();
	}

}

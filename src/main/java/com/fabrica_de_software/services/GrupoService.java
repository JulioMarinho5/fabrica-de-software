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
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.AlunoJaCadastradoEmGrupoException;
import com.fabrica_de_software.exceptions.AlunoNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProjetoNaoEncontradoException;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.AlunoRepository;
import com.fabrica_de_software.repositories.GrupoRepository;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.ProjetoRepository;

import jakarta.transaction.Transactional;

@Service
public class GrupoService {
	private final GrupoRepository grupoRepository;
	private final ProjetoRepository projetoRepository;
	private final ProfessorRepository professorRepository;
	private final AlunoRepository alunoRepository;
	private final ProfessorProducer professorProducer;

	public GrupoService(GrupoRepository grupoRepository, ProjetoRepository projetoRepository,
			ProfessorRepository professorRepository, AlunoRepository alunoRepository,
			ProfessorProducer professorProducer) {
		this.grupoRepository = grupoRepository;
		this.projetoRepository = projetoRepository;
		this.professorRepository = professorRepository;
		this.alunoRepository = alunoRepository;
		this.professorProducer = professorProducer;
	}

	public MensagemResponseDto criarNovoGrupo(CadastroGrupoRequestDto dto) {
		Projeto projeto = projetoRepository.findById(dto.projetoId())
				.orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado!"));
		Professor professor = professorRepository.findByEmail(dto.professorCoordenadorEmail().toLowerCase())
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
		for (Aluno a : alunosDoGrupo) {
			a.setGrupo(grupo);
		}
		alunoRepository.saveAll(alunosDoGrupo);
		projeto.setTemGrupo(true);
		projetoRepository.save(projeto);
		professorProducer.enviarEmailGrupo(professor.getUsuario().getEmail(),
				alunosDoGrupo.stream().map(a -> new AlunoResponseDto(a, a.getUsuario())).toList());
		return new MensagemResponseDto("Grupo criado com sucesso!", LocalDateTime.now());
	}

	@Transactional
	public List<GrupoResponseDto> listarGrupos() {
		return grupoRepository.findAllCompleto().stream().map(g -> new GrupoResponseDto(g.getId(),
				ProjetoResponseDto.builder().id(g.getProjeto().getId()).titulo(g.getProjeto().getTitulo())
						.objetivo(g.getProjeto().getObjetivo()).perfilUsuarios(g.getProjeto().getPerfilUsuarios())
						.localUtilizacao(g.getProjeto().getLocalUtilizacao())
						.funcionalidades(g.getProjeto().getFuncionalidades()).demanda(g.getProjeto().getDemanda())
						.dataInicio(g.getProjeto().getDataInicio())
						.professorResponsavel(new ProfessorResponseDto(g.getProjeto().getProfessor(),
								g.getProjeto().getProfessor().getUsuario()))
						.temGrupo(g.getProjeto().isTemGrupo()).build(),
				new ProfessorResponseDto(g.getProfessor(), g.getProfessor().getUsuario()),
				g.getAlunos().stream().map(a -> new AlunoResponseDto(a, a.getUsuario())).toList())).toList();
	}

}

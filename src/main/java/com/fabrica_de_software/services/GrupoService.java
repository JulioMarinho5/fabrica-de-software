package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.AlunoDTO;
import com.fabrica_de_software.dtos.CadastroGrupoDTO;
import com.fabrica_de_software.dtos.GrupoDTO;
import com.fabrica_de_software.dtos.MensagemDTO;
import com.fabrica_de_software.dtos.ProfessorDTO;
import com.fabrica_de_software.dtos.ProjetoDTO;
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
	private GrupoRepository grupoRepository;
	private ProjetoRepository projetoRepository;
	private ProfessorRepository professorRepository;
	private AlunoRepository alunoRepository;
	private ProfessorProducer professorProducer;

	public GrupoService(GrupoRepository grupoRepository, ProjetoRepository projetoRepository,
			ProfessorRepository professorRepository, AlunoRepository alunoRepository,
			ProfessorProducer professorProducer) {
		this.grupoRepository = grupoRepository;
		this.projetoRepository = projetoRepository;
		this.professorRepository = professorRepository;
		this.alunoRepository = alunoRepository;
		this.professorProducer = professorProducer;
	}

	public MensagemDTO criarNovoGrupo(CadastroGrupoDTO dto) {
		Projeto projeto = projetoRepository.findById(dto.getProjetoId())
				.orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado!"));
		Professor professor = professorRepository.findByEmail(dto.getProfessorCoordenadorEmail().toLowerCase())
				.orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
		List<Aluno> alunosDoGrupo = alunoRepository.findAllById(dto.getAlunosIds());
		if (alunosDoGrupo.size() != dto.getAlunosIds().size()) {
			throw new AlunoNaoEncontradoException("Um ou mais alunos informados não foram encontrados!");
		}
		for (Aluno a : alunosDoGrupo) {
			if (a.getGrupo() != null) {
				throw new AlunoJaCadastradoEmGrupoException("O aluno " + a.getNome() + " já faz parte de outro grupo!");
			}
		}
		Grupo grupo = new Grupo(projeto, professor, LocalDate.now(), Status.ATIVO);
		grupoRepository.save(grupo);
		for (Aluno a : alunosDoGrupo) {
			a.setGrupo(grupo);
		}
		alunoRepository.saveAll(alunosDoGrupo);
		projeto.setTemGrupo(true);
		projetoRepository.save(projeto);
		professorProducer.enviarEmailGrupo(professor.getEmail(),
				alunosDoGrupo.stream().map(a -> new AlunoDTO(a)).toList());
		return new MensagemDTO("Grupo criado com sucesso!", LocalDateTime.now());
	}
	
	@Transactional
	public List<GrupoDTO> listarGrupos() {
		return grupoRepository.findAllCompleto().stream().map(g -> new GrupoDTO(g.getId(),
				ProjetoDTO.builder().id(g.getProjeto().getId()).titulo(g.getProjeto().getTitulo())
						.objetivo(g.getProjeto().getObjetivo()).perfilUsuarios(g.getProjeto().getPerfilUsuarios())
						.localUtilizacao(g.getProjeto().getLocalUtilizacao())
						.funcionalidades(g.getProjeto().getFuncionalidades()).demanda(g.getProjeto().getDemanda())
						.dataInicio(g.getProjeto().getDataInicio())
						.professorResponsavel(new ProfessorDTO(g.getProjeto().getProfessor()))
						.temGrupo(g.getProjeto().isTemGrupo()).build(),
				new ProfessorDTO(g.getProfessor()), g.getAlunos().stream().map(AlunoDTO::new).toList())).toList();
	}

}

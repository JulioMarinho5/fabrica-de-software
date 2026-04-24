package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.fabrica_de_software.dtos.MensagemDTO;
import com.fabrica_de_software.dtos.ProfessorDTO;
import com.fabrica_de_software.dtos.ProjetoDTO;
import com.fabrica_de_software.dtos.SolicitacaoProjetoDTO;
import com.fabrica_de_software.dtos.StatusProjetoDTO;
import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.entities.Projeto;
import com.fabrica_de_software.enums.StatusProjeto;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProjetoNaoEncontradoException;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.ProjetoRepository;

@Service
public class ProjetoService {
	private ProjetoRepository projetoRepository;
	private ProfessorRepository professorRepository;
	private ValidarStatusService validarStatusService;
	private ProfessorProducer professorProducer;

	public ProjetoService(ProjetoRepository projetoRepository, ProfessorRepository professorRepository,
			ValidarStatusService validarStatusService, ProfessorProducer professorProducer) {
		this.projetoRepository = projetoRepository;
		this.professorRepository = professorRepository;
		this.validarStatusService = validarStatusService;
		this.professorProducer = professorProducer;
	}

	public MensagemDTO solicitarProjeto(SolicitacaoProjetoDTO dto) {
		Professor professor = professorRepository.findById(dto.getProfessorResponsavelId())
				.orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
		Projeto projeto = new Projeto(dto.getTitulo(), dto.getObjetivo(), dto.getPerfilUsuarios(),
				dto.getLocalUtilizacao(), dto.getFuncionalidades(), dto.getDemanda(), dto.getDataInicio(), null,
				professor, StatusProjeto.SOLICITADO, false);
		projetoRepository.save(projeto);
		return new MensagemDTO("Projeto solicitado com sucesso!", LocalDateTime.now());

	}

	public List<ProjetoDTO> listarProjetos(StatusProjeto status) {
		return projetoRepository.findByStatus(status).stream()
				.map(p -> ProjetoDTO.builder().id(p.getId()).titulo(p.getTitulo()).objetivo(p.getObjetivo())
						.perfilUsuarios(p.getPerfilUsuarios()).localUtilizacao(p.getLocalUtilizacao())
						.funcionalidades(p.getFuncionalidades()).demanda(p.getDemanda()).dataInicio(p.getDataInicio())
						.professorResponsavel(new ProfessorDTO(p.getProfessor())).temGrupo(p.isTemGrupo()).build())
				.toList();
	}

	public List<ProjetoDTO> listarProjetosProfessor(long professorid) {
		return projetoRepository.findById(professorid).stream()
				.map(p -> ProjetoDTO.builder().id(p.getId()).titulo(p.getTitulo()).objetivo(p.getObjetivo())
						.perfilUsuarios(p.getPerfilUsuarios()).localUtilizacao(p.getLocalUtilizacao())
						.funcionalidades(p.getFuncionalidades()).demanda(p.getDemanda()).dataInicio(p.getDataInicio())
						.professorResponsavel(new ProfessorDTO(p.getProfessor())).temGrupo(p.isTemGrupo()).build())
				.toList();
	}

	public MensagemDTO atualizarStatus(StatusProjetoDTO dto) {
		Projeto projeto = projetoRepository.findById(dto.getProjetoId())
				.orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado!"));
		StatusProjeto atual = projeto.getStatus();
		StatusProjeto novo = dto.getStatus();
		validarStatusService.ValidarTransacao(atual, novo);
		switch (dto.getStatus()) {
		case EM_ANALISE:
			projeto.setStatus(StatusProjeto.EM_ANALISE);
			professorProducer.enviarEmailEmAnalise(projeto.getProfessor().getEmail());
			break;
		case APROVADO:
			projeto.setStatus(StatusProjeto.APROVADO);
			projeto.setDataAprovacao(LocalDate.now());
			professorProducer.enviarEmailAprovacao(projeto.getProfessor().getEmail());
			break;
		case CANCELADO:
			projeto.setStatus(StatusProjeto.CANCELADO);
			professorProducer.enviarEmailCancelamento(projeto.getProfessor().getEmail());
			break;
		}
		projetoRepository.save(projeto);
		return new MensagemDTO("Projeto atualizado com sucesso!", LocalDateTime.now());
	}

}

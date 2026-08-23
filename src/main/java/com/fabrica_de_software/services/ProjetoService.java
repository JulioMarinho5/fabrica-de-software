package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.dtos.ProfessorResponseDto;
import com.fabrica_de_software.dtos.ProjetoResponseDto;
import com.fabrica_de_software.dtos.SolicitacaoProjetoRequestDto;
import com.fabrica_de_software.dtos.StatusProjetoRequestDto;
import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.entities.Projeto;
import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.enums.StatusProjeto;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProjetoNaoEncontradoException;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.ProjetoRepository;

@Service
public class ProjetoService {
	private final ProjetoRepository projetoRepository;
	private final ProfessorRepository professorRepository;
	private final ValidarStatusService validarStatusService;
	private final ProfessorProducer professorProducer;

	public ProjetoService(ProjetoRepository projetoRepository, ProfessorRepository professorRepository,
			ValidarStatusService validarStatusService, ProfessorProducer professorProducer) {
		this.projetoRepository = projetoRepository;
		this.professorRepository = professorRepository;
		this.validarStatusService = validarStatusService;
		this.professorProducer = professorProducer;
	}

	public MensagemResponseDto solicitarProjeto(SolicitacaoProjetoRequestDto dto, Usuario usuario) {
		Professor professor = professorRepository.findByUsuarioId(usuario.getId())
				.orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
		Projeto projeto = new Projeto(dto.titulo(), dto.objetivo(), dto.perfilUsuarios(), dto.localUtilizacao(),
				dto.funcionalidades(), dto.demanda(), dto.dataInicio(), null, professor, StatusProjeto.SOLICITADO,
				false);
		projetoRepository.save(projeto);
		return new MensagemResponseDto("Projeto solicitado com sucesso!", LocalDateTime.now());

	}

	public List<ProjetoResponseDto> listarProjetos(StatusProjeto status) {
		return projetoRepository.findByStatus(status).stream()
				.map(p -> ProjetoResponseDto.builder().id(p.getId()).titulo(p.getTitulo()).objetivo(p.getObjetivo())
						.perfilUsuarios(p.getPerfilUsuarios()).localUtilizacao(p.getLocalUtilizacao())
						.funcionalidades(p.getFuncionalidades()).demanda(p.getDemanda()).dataInicio(p.getDataInicio())
						.professorResponsavel(new ProfessorResponseDto(p.getProfessor(), null)).temGrupo(p.isTemGrupo())
						.build())
				.toList();
	}

	public List<ProjetoResponseDto> listarProjetosProfessor(Usuario usuario) {
		Professor professor = professorRepository.findByUsuarioId(usuario.getId())
				.orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
		return projetoRepository.findByProfessorId(professor.getId()).stream()
				.map(p -> ProjetoResponseDto.builder().id(p.getId()).titulo(p.getTitulo()).objetivo(p.getObjetivo())
						.perfilUsuarios(p.getPerfilUsuarios()).localUtilizacao(p.getLocalUtilizacao())
						.funcionalidades(p.getFuncionalidades()).demanda(p.getDemanda()).dataInicio(p.getDataInicio())
						.professorResponsavel(new ProfessorResponseDto(p.getProfessor(), p.getProfessor().getUsuario()))
						.temGrupo(p.isTemGrupo()).build())
				.toList();
	}

	public MensagemResponseDto atualizarStatus(StatusProjetoRequestDto dto) {
		Projeto projeto = projetoRepository.findById(dto.projetoId())
				.orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado!"));
		StatusProjeto atual = projeto.getStatus();
		StatusProjeto novo = dto.status();
		validarStatusService.ValidarTransacao(atual, novo);
		String usuarioEmail = projeto.getProfessor().getUsuario().getEmail();
		switch (dto.status()) {
		case EM_ANALISE:
			projeto.setStatus(StatusProjeto.EM_ANALISE);
			professorProducer.enviarEmailEmAnalise(usuarioEmail);
			break;
		case APROVADO:
			projeto.setStatus(StatusProjeto.APROVADO);
			projeto.setDataAprovacao(LocalDate.now());
			professorProducer.enviarEmailAprovacao(usuarioEmail);
			break;
		case CANCELADO:
			projeto.setStatus(StatusProjeto.CANCELADO);
			professorProducer.enviarEmailCancelamento(usuarioEmail);
			break;
		}
		projetoRepository.save(projeto);
		return new MensagemResponseDto("Projeto atualizado com sucesso!", LocalDateTime.now());
	}

}

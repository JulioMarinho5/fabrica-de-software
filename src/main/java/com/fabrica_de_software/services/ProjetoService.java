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
import com.fabrica_de_software.enums.StatusProjeto;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProjetoNaoEncontradoException;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.ProfessorRepository;
import com.fabrica_de_software.repositories.ProjetoRepository;

import io.jsonwebtoken.Claims;

@Service
public class ProjetoService {
	private ProjetoRepository projetoRepository;
	private ProfessorRepository professorRepository;
	private ValidarStatusService validarStatusService;
	private ProfessorProducer professorProducer;
	private JwtService jwtService;

	public ProjetoService(ProjetoRepository projetoRepository, ProfessorRepository professorRepository,
			ValidarStatusService validarStatusService, ProfessorProducer professorProducer, JwtService jwtService) {
		this.projetoRepository = projetoRepository;
		this.professorRepository = professorRepository;
		this.validarStatusService = validarStatusService;
		this.professorProducer = professorProducer;
		this.jwtService = jwtService;
	}

	public MensagemResponseDto solicitarProjeto(SolicitacaoProjetoRequestDto dto, String token) {
		Claims claims = jwtService.validarToken(token);
		long professorId = claims.get("professorId", Long.class);
		Professor professor = professorRepository.findById(professorId)
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

	public List<ProjetoResponseDto> listarProjetosProfessor(String token) {
		Claims claims = jwtService.validarToken(token);
		long professorId = claims.get("professorId", Long.class);
		return projetoRepository.findByProfessorId(professorId).stream()
				.map(p -> ProjetoResponseDto.builder().id(p.getId()).titulo(p.getTitulo()).objetivo(p.getObjetivo())
						.perfilUsuarios(p.getPerfilUsuarios()).localUtilizacao(p.getLocalUtilizacao())
						.funcionalidades(p.getFuncionalidades()).demanda(p.getDemanda()).dataInicio(p.getDataInicio())
						.professorResponsavel(new ProfessorResponseDto(p.getProfessor(), null)).temGrupo(p.isTemGrupo())
						.build())
				.toList();
	}

	public MensagemResponseDto atualizarStatus(StatusProjetoRequestDto dto) {
		Projeto projeto = projetoRepository.findById(dto.projetoId())
				.orElseThrow(() -> new ProjetoNaoEncontradoException("Projeto não encontrado!"));
		StatusProjeto atual = projeto.getStatus();
		StatusProjeto novo = dto.status();
		validarStatusService.ValidarTransacao(atual, novo);
		switch (dto.status()) {
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
		return new MensagemResponseDto("Projeto atualizado com sucesso!", LocalDateTime.now());
	}

}

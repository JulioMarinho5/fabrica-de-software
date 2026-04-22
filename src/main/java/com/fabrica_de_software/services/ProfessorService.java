package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.CadastroProfessorDTO;
import com.fabrica_de_software.dtos.LoginProfessorDTO;
import com.fabrica_de_software.dtos.MensagemDTO;
import com.fabrica_de_software.dtos.ProfessorDTO;
import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.ProfessorJaCadastradoException;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.SenhaIncorretaException;
import com.fabrica_de_software.notificacoes.ProfessorProducer;
import com.fabrica_de_software.repositories.ProfessorRepository;

@Service
public class ProfessorService {
	private ProfessorRepository professorRepository;
	private GeradorDeRaService geradorRa;
	private ProfessorProducer professorProducer;
	private PasswordEncoder passwordEncoder;

	public ProfessorService(ProfessorRepository professorRepository, GeradorDeRaService geradorRa,
			ProfessorProducer professorProducer, PasswordEncoder passwordEncoder) {
		this.professorRepository = professorRepository;
		this.geradorRa = geradorRa;
		this.professorProducer = professorProducer;
		this.passwordEncoder = passwordEncoder;
	}

	public MensagemDTO cadastrarProfessor(CadastroProfessorDTO dto) {
		Optional<Professor> op = professorRepository.findByEmail(dto.getEmail().toLowerCase());
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
		Professor professor = new Professor(ra, dto.getNome(), dto.getEmail().toLowerCase(), dto.getTelefone(),
				dto.getEscola(), LocalDate.now(), Status.ATIVO, passwordEncoder.encode(ra));
		professorRepository.save(professor);
		professorProducer.enviarEmailCadastro(professor.getEmail(), ra);
		return new MensagemDTO("Professor cadastrado com sucesso!", LocalDateTime.now());

	}

	public ProfessorDTO loginProfessor(LoginProfessorDTO dto) {
		return professorRepository.findByEmail(dto.getEmail().toLowerCase()).map(p -> {
			if (!passwordEncoder.matches(dto.getSenha(), p.getSenha())) {
				throw new SenhaIncorretaException("Senha incorreta! Tente novamente");
			}
			return new ProfessorDTO(p);
		}).orElseThrow(() -> new ProfessorNaoEncontradoException("Professor não encontrado!"));
	}

}

package com.fabrica_de_software.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.AlunoDTO;
import com.fabrica_de_software.dtos.CadastroAlunoDTO;
import com.fabrica_de_software.dtos.MensagemDTO;
import com.fabrica_de_software.entities.Aluno;
import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.exceptions.AlunoJaCadastradoException;
import com.fabrica_de_software.repositories.AlunoRepository;

@Service
public class AlunoService {
	private AlunoRepository alunoRepository;
	private GeradorDeRaService geradorRa;

	public AlunoService(AlunoRepository alunoRepository, GeradorDeRaService geradorRa) {
		this.alunoRepository = alunoRepository;
		this.geradorRa = geradorRa;
	}

	public MensagemDTO cadastrarAluno(CadastroAlunoDTO dto) {
		Optional<Aluno> op = alunoRepository.findByEmail(dto.getEmail().toLowerCase());
		if (op.isPresent()) {
			throw new AlunoJaCadastradoException("Um aluno com esse Email já está cadastrado");
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
		Aluno aluno = new Aluno(ra, dto.getNome(), dto.getEmail().toLowerCase(), dto.getTelefone(), dto.getCurso(),
				dto.getTurno(), dto.getHorasSemanais(), dto.getGithubUrl(), dto.getLinkedinUrl(), dto.getDataSelecao(),
				LocalDate.now(), null, Status.ATIVO);
		alunoRepository.save(aluno);
		return new MensagemDTO("Aluno cadastrado com sucesso!", LocalDateTime.now());
	}

	public List<AlunoDTO> listarAlunosDisponiveis() {
		List<Aluno> alunos = alunoRepository.findByGrupoIdIsNull();
		return alunos.stream()
				.map(a -> new AlunoDTO(a))
				.toList();
	}

}

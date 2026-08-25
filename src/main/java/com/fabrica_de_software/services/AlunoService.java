package com.fabrica_de_software.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.entities.Aluno;
import com.fabrica_de_software.repositories.AlunoRepository;

@Service
public class AlunoService {
	private final AlunoRepository alunoRepository;

	public AlunoService(AlunoRepository alunoRepository) {
		this.alunoRepository = alunoRepository;
	}

	public List<AlunoResponseDto> listarAlunosDisponiveis() {
		List<Aluno> alunos = alunoRepository.findByGrupoIdIsNull();
		return alunos.stream().map(a -> new AlunoResponseDto(a, a.getUsuario())).toList();
	}

	public List<AlunoResponseDto> listarTodosOsAlunos() {
		return alunoRepository.findAll().stream().map(a -> new AlunoResponseDto(a, a.getUsuario())).toList();
	}

}

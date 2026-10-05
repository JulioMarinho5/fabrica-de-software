package com.fabrica_de_software.dtos;

import java.util.List;

public record GrupoResponseDto(long id, ProjetoResponseDto projeto, ProfessorResponseDto professorCoordenador,
		List<AlunoResponseDto> alunos) {
}

package com.fabrica_de_software.dtos;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroGrupoRequestDto(@NotNull(message = "O ID do projeto é obrigatório") long projetoId,

		@NotBlank(message = "O email do professor coordenador é obrigatório") @Email(message = "Email inválido") String professorCoordenadorEmail,

		List<Long> alunosIds) {

	public CadastroGrupoRequestDto {
		professorCoordenadorEmail = professorCoordenadorEmail == null ? null
				: professorCoordenadorEmail.toLowerCase().trim();
	}
}

package com.fabrica_de_software.dtos;

import com.fabrica_de_software.enums.StatusProjeto;

import jakarta.validation.constraints.NotNull;

public record StatusProjetoRequestDto(@NotNull(message = "O ID do projeto é obrigatório") long projetoId,

		@NotNull(message = "O status é obrigatório") StatusProjeto status) {
}

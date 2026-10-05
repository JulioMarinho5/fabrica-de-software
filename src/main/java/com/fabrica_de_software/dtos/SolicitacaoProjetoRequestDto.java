package com.fabrica_de_software.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SolicitacaoProjetoRequestDto(@NotBlank(message = "O título é obrigatório") String titulo,

		@NotBlank(message = "O objetivo é obrigatório") String objetivo,

		@NotBlank(message = "O perfil dos usuários é obrigatório") String perfilUsuarios,

		@NotBlank(message = "O local de utilização é obrigatório") String localUtilizacao,

		@NotBlank(message = "As funcionalidades são obrigatórias") String funcionalidades,

		@NotBlank(message = "A demanda é obrigatória") String demanda,

		@NotNull(message = "A data de início é obrigatória") LocalDate dataInicio) {
}

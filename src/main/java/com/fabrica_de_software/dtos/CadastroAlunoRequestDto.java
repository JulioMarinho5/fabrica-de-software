package com.fabrica_de_software.dtos;

import java.time.LocalDate;

import com.fabrica_de_software.enums.Turno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastroAlunoRequestDto(
		@NotBlank(message = "O nome é obrigatório") @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres") String nome,

		@NotBlank(message = "O email é obrigatório") @Email(message = "Email inválido") @Size(max = 150, message = "O email deve ter no máximo 150 caracteres") String email,

		@NotBlank(message = "O telefone é obrigatório") @Size(min = 11, max = 11, message = "O telefone deve conter exatamente 11 caracteres") String telefone,

		@NotBlank(message = "O curso é obrigatório") @Size(max = 150, message = "O curso deve ter no máximo 150 caracteres") String curso,

		@NotNull(message = "O turno é obrigatório") Turno turno,

		@Min(value = 1, message = "As horas semanais devem ser maiores que zero") int horasSemanais,

		@NotNull(message = "A data de seleção é obrigatória") LocalDate dataSelecao,

		@Size(max = 255, message = "A URL do GitHub deve ter no máximo 255 caracteres") String githubUrl,

		@Size(max = 255, message = "A URL do LinkedIn deve ter no máximo 255 caracteres") String linkedinUrl) {
}
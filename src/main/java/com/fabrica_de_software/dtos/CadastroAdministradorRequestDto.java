package com.fabrica_de_software.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroAdministradorRequestDto(
		@NotBlank(message = "O nome é obrigatório") @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres") String nome,

		@NotBlank(message = "O email é obrigatório") @Email(message = "Email inválido") @Size(max = 150, message = "O email deve ter no máximo 150 caracteres") String email,

		@NotBlank(message = "O telefone é obrigatório") @Size(min = 11, max = 11, message = "O telefone deve conter exatamente 11 caracteres") String telefone,

		@NotBlank(message = "A escola é obrigatória") @Size(max = 150, message = "A escola deve ter no máximo 150 caracteres") String escola) {

	public CadastroAdministradorRequestDto {
		email = email == null ? null : email.toLowerCase().trim();
	}

}

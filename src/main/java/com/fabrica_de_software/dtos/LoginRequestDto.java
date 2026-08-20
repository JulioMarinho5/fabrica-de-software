package com.fabrica_de_software.dtos;

import jakarta.validation.constraints.*;

public record LoginRequestDto(
		@NotBlank(message = "O email é obrigatório") @Email(message = "Email inválido") String email,

		@NotBlank(message = "A senha é obrigatória") String senha) {
	public LoginRequestDto {
		email = email == null ? null : email.toLowerCase().trim();
	}
}

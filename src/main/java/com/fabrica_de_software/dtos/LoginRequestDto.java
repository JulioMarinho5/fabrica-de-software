package com.fabrica_de_software.dtos;

import jakarta.validation.constraints.*;

public record LoginRequestDto(
		@NotBlank(message = "O email é obrigatório") @Email(message = "Email inválido") String email,

		@NotBlank(message = "A senha é obrigatória") String senha,

		@NotBlank String portal) {
	public LoginRequestDto {
		email = email == null ? null : email.toLowerCase().trim();
		portal = portal == null ? null : "ROLE_" + portal.toUpperCase().trim();
	}
}

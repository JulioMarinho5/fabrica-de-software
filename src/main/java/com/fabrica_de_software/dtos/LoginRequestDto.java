package com.fabrica_de_software.dtos;

import com.fabrica_de_software.enums.Portal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
		@NotBlank(message = "O email é obrigatório") @Email(message = "Email inválido") String email,

		@NotBlank(message = "A senha é obrigatória") String senha,

		Portal portal) {
	public LoginRequestDto {
		email = email == null ? null : email.toLowerCase().trim();
	}
}

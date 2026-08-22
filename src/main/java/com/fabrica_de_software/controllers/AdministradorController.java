package com.fabrica_de_software.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fabrica_de_software.dtos.CadastroAdministradorRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.services.AdministradorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/adm")
public class AdministradorController {
	private final AdministradorService administradorService;

	public AdministradorController(AdministradorService administradorService) {
		this.administradorService = administradorService;
	}

	@PostMapping("/cadastro")
	public ResponseEntity<MensagemResponseDto> cadastrarAdmin(@Valid @RequestBody CadastroAdministradorRequestDto dto) {
		MensagemResponseDto data = administradorService.cadastrarAdmin(dto);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> loginAdmin(@Valid @RequestBody LoginRequestDto dto) {
		LoginResponseDto data = administradorService.loginAdmin(dto);
		return ResponseEntity.ok(data);
	}

}

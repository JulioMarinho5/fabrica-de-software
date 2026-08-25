package com.fabrica_de_software.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabrica_de_software.dtos.CadastroAdministradorRequestDto;
import com.fabrica_de_software.dtos.CadastroAlunoRequestDto;
import com.fabrica_de_software.dtos.CadastroProfessorRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
		LoginResponseDto data = authService.login(dto);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/register/adm")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MensagemResponseDto> cadastrarAdmin(@Valid @RequestBody CadastroAdministradorRequestDto dto) {
		MensagemResponseDto data = authService.cadastrarAdmin(dto);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/register/professor")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MensagemResponseDto> cadastrarProfessor(@Valid @RequestBody CadastroProfessorRequestDto dto) {
		MensagemResponseDto data = authService.cadastrarProfessor(dto);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/register/aluno")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MensagemResponseDto> cadastrarAluno(@Valid @RequestBody CadastroAlunoRequestDto dto) {
		MensagemResponseDto data = authService.cadastrarAluno(dto);
		return ResponseEntity.ok(data);

	}

}

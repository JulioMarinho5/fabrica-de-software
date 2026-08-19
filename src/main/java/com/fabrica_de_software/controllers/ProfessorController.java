package com.fabrica_de_software.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabrica_de_software.dtos.CadastroProfessorDTO;
import com.fabrica_de_software.dtos.LoginProfessorDTO;
import com.fabrica_de_software.dtos.MensagemDTO;
import com.fabrica_de_software.dtos.ProfessorDTO;
import com.fabrica_de_software.dtos.TokenLoginProfessorDTO;
import com.fabrica_de_software.services.ProfessorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
	private ProfessorService professorService;

	public ProfessorController(ProfessorService professorService) {
		this.professorService = professorService;
	}

	@PostMapping("/cadastro")
	public ResponseEntity<MensagemDTO> cadastrarProfessor(@Valid @RequestBody CadastroProfessorDTO dto) {
		MensagemDTO data = professorService.cadastrarProfessor(dto);
		return ResponseEntity.ok(data);
	}

	@PostMapping("/login")
	public ResponseEntity<TokenLoginProfessorDTO> loginProfessor(@Valid @RequestBody LoginProfessorDTO dto) {
		TokenLoginProfessorDTO data = professorService.loginProfessor(dto);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/lista")
	public ResponseEntity<List<ProfessorDTO>> listarProfessores() {
		List<ProfessorDTO> lista = professorService.listarProfessores();
		return ResponseEntity.ok(lista);
	}

}

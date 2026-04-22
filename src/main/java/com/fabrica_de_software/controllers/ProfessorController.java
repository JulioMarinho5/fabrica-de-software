package com.fabrica_de_software.controllers;

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

	@GetMapping("/login")
	public ResponseEntity<ProfessorDTO> loginProfessor(@Valid @RequestBody LoginProfessorDTO dto) {
		ProfessorDTO data = professorService.loginProfessor(dto);
		return ResponseEntity.ok(data);
	}

}

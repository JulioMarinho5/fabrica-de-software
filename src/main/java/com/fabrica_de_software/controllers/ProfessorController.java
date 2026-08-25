package com.fabrica_de_software.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fabrica_de_software.dtos.ProfessorResponseDto;
import com.fabrica_de_software.services.ProfessorService;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
	private ProfessorService professorService;

	public ProfessorController(ProfessorService professorService) {
		this.professorService = professorService;
	}

	@GetMapping("/lista")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ProfessorResponseDto>> listarProfessores() {
		List<ProfessorResponseDto> lista = professorService.listarProfessores();
		return ResponseEntity.ok(lista);
	}

}

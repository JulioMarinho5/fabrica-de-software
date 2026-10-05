package com.fabrica_de_software.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fabrica_de_software.dtos.AlunoResponseDto;

import com.fabrica_de_software.services.AlunoService;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
	private AlunoService alunoService;

	public AlunoController(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	@GetMapping("/disponiveis")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AlunoResponseDto>> listarAlunosDisponiveis() {
		List<AlunoResponseDto> alunosDisponiveis = alunoService.listarAlunosDisponiveis();
		return ResponseEntity.ok(alunosDisponiveis);
	}

	@GetMapping("/todos")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AlunoResponseDto>> listarTodosOsAlunos() {
		List<AlunoResponseDto> alunos = alunoService.listarTodosOsAlunos();
		return ResponseEntity.ok(alunos);
	}

}

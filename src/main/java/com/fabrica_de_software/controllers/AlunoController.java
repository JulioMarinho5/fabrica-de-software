package com.fabrica_de_software.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabrica_de_software.dtos.AlunoDTO;
import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.CadastroAlunoDTO;
import com.fabrica_de_software.dtos.CadastroAlunoRequestDto;
import com.fabrica_de_software.dtos.MensagemDTO;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.services.AlunoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
	private AlunoService alunoService;

	public AlunoController(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

	@PostMapping("/cadastro")
	public ResponseEntity<MensagemResponseDto> cadastrarAluno(@Valid @RequestBody CadastroAlunoRequestDto dto) {
		MensagemResponseDto data = alunoService.cadastrarAluno(dto);
		return ResponseEntity.ok(data);

	}

	@GetMapping("/disponiveis")
	public ResponseEntity<List<AlunoResponseDto>> listarAlunosDisponiveis() {
		List<AlunoResponseDto> alunosDisponiveis = alunoService.listarAlunosDisponiveis();
		return ResponseEntity.ok(alunosDisponiveis);
	}

	@GetMapping("/todos")
	public ResponseEntity<List<AlunoResponseDto>> listarTodosOsAlunos() {
		List<AlunoResponseDto> alunos = alunoService.listarTodosOsAlunos();
		return ResponseEntity.ok(alunos);
	}

}

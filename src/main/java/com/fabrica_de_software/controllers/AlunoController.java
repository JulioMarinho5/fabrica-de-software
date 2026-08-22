package com.fabrica_de_software.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.CadastroAlunoRequestDto;
import com.fabrica_de_software.dtos.LoginRequestDto;
import com.fabrica_de_software.dtos.LoginResponseDto;
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
	@PreAuthorize("HasRole('ADMIN')")
	public ResponseEntity<MensagemResponseDto> cadastrarAluno(@Valid @RequestBody CadastroAlunoRequestDto dto) {
		MensagemResponseDto data = alunoService.cadastrarAluno(dto);
		return ResponseEntity.ok(data);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> loginAluno(@Valid @RequestBody LoginRequestDto dto) {
		LoginResponseDto data = alunoService.loginAluno(dto);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/disponiveis")
	@PreAuthorize("HasRole('ADMIN')")
	public ResponseEntity<List<AlunoResponseDto>> listarAlunosDisponiveis() {
		List<AlunoResponseDto> alunosDisponiveis = alunoService.listarAlunosDisponiveis();
		return ResponseEntity.ok(alunosDisponiveis);
	}

	@GetMapping("/todos")
	@PreAuthorize("HasRole('ADMIN')")
	public ResponseEntity<List<AlunoResponseDto>> listarTodosOsAlunos() {
		List<AlunoResponseDto> alunos = alunoService.listarTodosOsAlunos();
		return ResponseEntity.ok(alunos);
	}

}

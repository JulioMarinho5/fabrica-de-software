package com.fabrica_de_software.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.dtos.ProjetoResponseDto;
import com.fabrica_de_software.dtos.SolicitacaoProjetoRequestDto;
import com.fabrica_de_software.dtos.StatusProjetoRequestDto;
import com.fabrica_de_software.entities.Usuario;
import com.fabrica_de_software.enums.StatusProjeto;
import com.fabrica_de_software.services.ProjetoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {
	private ProjetoService projetoService;

	public ProjetoController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	@PostMapping("/envio")
	@PreAuthorize("hasRole('PROFESSOR')")
	public ResponseEntity<MensagemResponseDto> solicitarProjeto(@Valid @RequestBody SolicitacaoProjetoRequestDto dto,
			@AuthenticationPrincipal(expression = "usuario") Usuario usuario) {
		MensagemResponseDto data = projetoService.solicitarProjeto(dto, usuario);
		return ResponseEntity.ok(data);

	}

	@GetMapping("/lista")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ProjetoResponseDto>> listarProjetos(@RequestParam StatusProjeto status) {
		List<ProjetoResponseDto> projetos = projetoService.listarProjetos(status);
		return ResponseEntity.ok(projetos);
	}

	@GetMapping("/professor")
	@PreAuthorize("hasRole('PROFESSOR')")
	public ResponseEntity<List<ProjetoResponseDto>> listarProjetosProfessor(
			@AuthenticationPrincipal(expression = "usuario") Usuario usuario) {
		List<ProjetoResponseDto> projetos = projetoService.listarProjetosProfessor(usuario);
		return ResponseEntity.ok(projetos);
	}

	@PatchMapping("/status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MensagemResponseDto> atualizarStatus(@RequestBody StatusProjetoRequestDto dto) {
		MensagemResponseDto data = projetoService.atualizarStatus(dto);
		return ResponseEntity.ok(data);
	}

}

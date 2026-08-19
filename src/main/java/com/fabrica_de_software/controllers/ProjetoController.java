package com.fabrica_de_software.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fabrica_de_software.dtos.MensagemDTO;
import com.fabrica_de_software.dtos.ProjetoDTO;
import com.fabrica_de_software.dtos.SolicitacaoProjetoDTO;
import com.fabrica_de_software.dtos.StatusProjetoDTO;
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
	public ResponseEntity<MensagemDTO> solicitarProjeto(@Valid @RequestBody SolicitacaoProjetoDTO dto,
			@RequestHeader("Authorization") String token) {
		MensagemDTO data = projetoService.solicitarProjeto(dto, token);
		return ResponseEntity.ok(data);

	}

	@GetMapping("/lista")
	public ResponseEntity<List<ProjetoDTO>> listarProjetos(@RequestParam StatusProjeto status) {
		List<ProjetoDTO> projetos = projetoService.listarProjetos(status);
		return ResponseEntity.ok(projetos);
	}

	@GetMapping("/professor")
	public ResponseEntity<List<ProjetoDTO>> listarProjetosProfessor(@RequestHeader("Authorization") String token) {
		List<ProjetoDTO> projetos = projetoService.listarProjetosProfessor(token);
		return ResponseEntity.ok(projetos);
	}

	@PatchMapping("/status")
	public ResponseEntity<MensagemDTO> atualizarStatus(@RequestBody StatusProjetoDTO dto) {
		MensagemDTO data = projetoService.atualizarStatus(dto);
		return ResponseEntity.ok(data);
	}

}

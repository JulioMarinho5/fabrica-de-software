package com.fabrica_de_software.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fabrica_de_software.dtos.CadastroGrupoRequestDto;
import com.fabrica_de_software.dtos.GrupoResponseDto;
import com.fabrica_de_software.dtos.MensagemResponseDto;
import com.fabrica_de_software.services.GrupoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/grupo")
public class GrupoController {

	private GrupoService grupoService;

	public GrupoController(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	@PostMapping("/novo-grupo")
	@PreAuthorize("HasRole('ADMIN')")
	public ResponseEntity<MensagemResponseDto> criarNovoGrupo(@Valid @RequestBody CadastroGrupoRequestDto dto) {
		MensagemResponseDto data = grupoService.criarNovoGrupo(dto);
		return ResponseEntity.ok(data);
	}

	@GetMapping("/lista")
	@PreAuthorize("HasRole('ADMIN')")
	public ResponseEntity<List<GrupoResponseDto>> listarGrupos() {
		List<GrupoResponseDto> lista = grupoService.listarGrupos();
		return ResponseEntity.ok(lista);
	}

}

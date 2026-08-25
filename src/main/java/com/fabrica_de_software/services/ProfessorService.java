package com.fabrica_de_software.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.ProfessorResponseDto;
import com.fabrica_de_software.repositories.ProfessorRepository;

@Service
public class ProfessorService {
	private final ProfessorRepository professorRepository;

	public ProfessorService(ProfessorRepository professorRepository) {
		this.professorRepository = professorRepository;
	}

	public List<ProfessorResponseDto> listarProfessores() {
		return professorRepository.findAll().stream().map(p -> new ProfessorResponseDto(p, p.getUsuario())).toList();
	}

}

package com.fabrica_de_software.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabrica_de_software.entities.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	Optional<Professor> findByEmail(String Email);

	Optional<Professor> findByUsuarioId(long usuarioId);
}

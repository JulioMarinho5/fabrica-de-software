package com.fabrica_de_software.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fabrica_de_software.entities.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	@Query("SELECT DISTINCT g FROM Grupo g " + "JOIN FETCH g.projeto p " + "JOIN FETCH p.professor "
			+ "JOIN FETCH g.professor " + "LEFT JOIN FETCH g.alunos")
	List<Grupo> findAllCompleto();
}

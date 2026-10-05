package com.fabrica_de_software.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fabrica_de_software.entities.Projeto;
import com.fabrica_de_software.enums.StatusProjeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
	List<Projeto> findByStatus(StatusProjeto status);

	List<Projeto> findByProfessorId(long professorId);

	@Query("""
			    SELECT p FROM Projeto p
			    JOIN FETCH p.grupo g
			    JOIN FETCH g.professor
			    JOIN FETCH g.alunos
			    WHERE p.status = :status
			""")
	List<Projeto> buscarProjetosAprovadosComTudo(StatusProjeto status);

}

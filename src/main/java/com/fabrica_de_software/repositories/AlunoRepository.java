package com.fabrica_de_software.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabrica_de_software.entities.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	
	Optional<Aluno> findByUsuarioId(long usuarioId);

	List<Aluno> findByGrupoIdIsNull();

	List<Aluno> findByGrupoId(long grupoId);

	boolean existsByGithubUrl(String githubUrl);

	boolean existsByLinkedinUrl(String LinkedinUrl);

}

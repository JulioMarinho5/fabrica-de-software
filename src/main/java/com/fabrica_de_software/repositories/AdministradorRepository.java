package com.fabrica_de_software.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabrica_de_software.entities.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
	Optional<Administrador> findByEmail(String email);

}

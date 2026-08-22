package com.fabrica_de_software.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabrica_de_software.entities.Role;
import com.fabrica_de_software.enums.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRole(RoleEnum role);
}

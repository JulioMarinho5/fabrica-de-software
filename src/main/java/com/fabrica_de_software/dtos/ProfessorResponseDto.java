package com.fabrica_de_software.dtos;

import com.fabrica_de_software.entities.Professor;
import com.fabrica_de_software.entities.Usuario;

public class ProfessorResponseDto {
	private long id;
	private String nome;
	private String email;
	private String telefone;

	public ProfessorResponseDto() {

	}

	public ProfessorResponseDto(Professor professor, Usuario usuario) {
		this.id = professor.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.telefone = usuario.getTelefone();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}

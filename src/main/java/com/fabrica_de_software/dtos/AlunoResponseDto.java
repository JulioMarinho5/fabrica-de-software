package com.fabrica_de_software.dtos;

import com.fabrica_de_software.entities.Aluno;
import com.fabrica_de_software.entities.Usuario;

public class AlunoResponseDto {
	private long id;
	private String nome;
	private String ra;
	private String telefone;
	private String email;

	public AlunoResponseDto() {
	}

	public AlunoResponseDto(Aluno aluno, Usuario usuario) {
		this.id = aluno.getId();
		this.nome = usuario.getNome();
		this.ra = aluno.getRa();
		this.telefone = usuario.getTelefone();
		this.email = usuario.getEmail();
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

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

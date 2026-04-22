package com.fabrica_de_software.dtos;

public class CadastroProfessorDTO {
	private String nome;
	private String email;
	private String telefone;
	private String escola;

	public CadastroProfessorDTO() {

	}

	public CadastroProfessorDTO(String nome, String email, String telefone, String escola) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.escola = escola;
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

	public String getEscola() {
		return escola;
	}

	public void setEscola(String escola) {
		this.escola = escola;
	}

}

package com.fabrica_de_software.dtos;

public class TokenLoginProfessorDTO {
	private String token;
	private String nome;
	private String email;
	private String telefone;

	public TokenLoginProfessorDTO() {
	}

	public TokenLoginProfessorDTO(String token, String nome, String email, String telefone) {
		super();
		this.token = token;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

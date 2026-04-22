package com.fabrica_de_software.dtos;

public class LoginProfessorDTO {
	private String email;
	private String senha;

	public LoginProfessorDTO() {
	}

	public LoginProfessorDTO(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}

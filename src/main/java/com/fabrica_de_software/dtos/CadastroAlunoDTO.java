package com.fabrica_de_software.dtos;

import java.time.LocalDate;

import com.fabrica_de_software.enums.Turno;

public class CadastroAlunoDTO {
	private String nome;
	private String email;
	private String telefone;
	private String curso;
	private Turno turno;
	private int horasSemanais;
	private LocalDate dataSelecao;
	private String githubUrl;
	private String linkedinUrl;

	public CadastroAlunoDTO() {
	}

	public CadastroAlunoDTO(String nome, String email, String telefone, String curso, Turno turno, int horasSemanais,
			LocalDate dataSelecao, String githubUrl, String linkedinUrl) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.curso = curso;
		this.turno = turno;
		this.horasSemanais = horasSemanais;
		this.dataSelecao = dataSelecao;
		this.githubUrl = githubUrl;
		this.linkedinUrl = linkedinUrl;
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

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public int getHorasSemanais() {
		return horasSemanais;
	}

	public void setHorasSemanais(int horasSemanais) {
		this.horasSemanais = horasSemanais;
	}

	public LocalDate getDataSelecao() {
		return dataSelecao;
	}

	public void setDataSelecao(LocalDate dataSelecao) {
		this.dataSelecao = dataSelecao;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

}

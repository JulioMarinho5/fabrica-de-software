package com.fabrica_de_software.entities;

import java.time.LocalDate;

import com.fabrica_de_software.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "professores")
public class Professor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true, columnDefinition = "CHAR(5)")
	private String ra;
	@Column(nullable = false, length = 150)
	private String nome;
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	@Column(nullable = false, unique = true, columnDefinition = "CHAR(11)")
	private String telefone;
	@Column(nullable = false, length = 150)
	private String escola;
	@Column(nullable = false, name = "data_cadastro")
	private LocalDate dataCadastro;
	@Column(nullable = true, length = 20)
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(nullable = false, length = 255)
	private String senha;

	public Professor() {
	}

	public Professor(String ra, String nome, String email, String telefone, String escola, LocalDate dataCadastro,
			Status status, String senha) {
		this.ra = ra;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.escola = escola;
		this.dataCadastro = dataCadastro;
		this.status = status;
		this.senha = senha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
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

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}

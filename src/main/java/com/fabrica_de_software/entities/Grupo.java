package com.fabrica_de_software.entities;

import java.time.LocalDate;
import java.util.List;

import com.fabrica_de_software.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grupos")
public class Grupo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne
	@JoinColumn(name = "projeto_id", nullable = false)
	private Projeto projeto;
	@ManyToOne
	@JoinColumn(name = "professor_coordenador_id", nullable = false)
	private Professor professor;
	@Column(nullable = false, name = "data_criacao")
	private LocalDate dataCriacao;
	@Column(nullable = true, length = 20)
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToMany(mappedBy = "grupo")
	private List<Aluno> alunos;

	public Grupo() {
	}

	public Grupo(Projeto projeto, Professor professor, LocalDate dataCriacao, Status status) {
		super();
		this.projeto = projeto;
		this.professor = professor;
		this.dataCriacao = dataCriacao;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

}

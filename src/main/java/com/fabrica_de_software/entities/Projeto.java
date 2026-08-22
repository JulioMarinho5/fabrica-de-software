package com.fabrica_de_software.entities;

import java.time.LocalDate;
import com.fabrica_de_software.enums.StatusProjeto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "projetos")
public class Projeto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String titulo;
	@Column(nullable = false)
	private String objetivo;
	@Column(nullable = false, name = "perfil_usuarios")
	private String perfilUsuarios;
	@Column(nullable = false, name = "local_utilizacao")
	private String localUtilizacao;
	@Column(nullable = false)
	private String funcionalidades;
	@Column(nullable = false)
	private String demanda;
	@Column(nullable = false, name = "data_inicio")
	private LocalDate dataInicio;
	@Column(nullable = false, name = "data_aprovacao")
	private LocalDate dataAprovacao;
	@ManyToOne
	@JoinColumn(name = "professor_responsavel_id", nullable = false)
	private Professor professor;
	@Column(nullable = true, length = 20)
	@Enumerated(EnumType.STRING)
	private StatusProjeto status;
	@Column(name = "tem_grupo", nullable = false)
	private boolean temGrupo;
	@OneToOne(mappedBy = "projeto")
	private Grupo grupo;

	public Projeto() {
	}

	public Projeto(String titulo, String objetivo, String perfilUsuarios, String localUtilizacao,
			String funcionalidades, String demanda, LocalDate dataInicio, LocalDate dataAprovacao, Professor professor,
			StatusProjeto status, boolean temGrupo) {
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.perfilUsuarios = perfilUsuarios;
		this.localUtilizacao = localUtilizacao;
		this.funcionalidades = funcionalidades;
		this.demanda = demanda;
		this.dataInicio = dataInicio;
		this.dataAprovacao = dataAprovacao;
		this.professor = professor;
		this.status = status;
		this.temGrupo = temGrupo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getPerfilUsuarios() {
		return perfilUsuarios;
	}

	public void setPerfilUsuarios(String perfilUsuarios) {
		this.perfilUsuarios = perfilUsuarios;
	}

	public String getLocalUtilizacao() {
		return localUtilizacao;
	}

	public void setLocalUtilizacao(String localUtilizacao) {
		this.localUtilizacao = localUtilizacao;
	}

	public String getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(String funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public String getDemanda() {
		return demanda;
	}

	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataAprovacao() {
		return dataAprovacao;
	}

	public void setDataAprovacao(LocalDate dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public StatusProjeto getStatus() {
		return status;
	}

	public void setStatus(StatusProjeto status) {
		this.status = status;
	}

	public boolean isTemGrupo() {
		return temGrupo;
	}

	public void setTemGrupo(boolean temGrupo) {
		this.temGrupo = temGrupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}

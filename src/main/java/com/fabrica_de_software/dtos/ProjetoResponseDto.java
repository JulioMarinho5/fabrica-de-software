package com.fabrica_de_software.dtos;

import java.time.LocalDate;

import com.fabrica_de_software.entities.Projeto;
// Importe a sua entidade Projeto aqui, caso esteja em outro pacote:
// import com.fabrica_de_software.models.Projeto;

public class ProjetoResponseDto {
	private long id;
	private String titulo;
	private String objetivo;
	private String perfilUsuarios;
	private String localUtilizacao;
	private String funcionalidades;
	private String demanda;
	private LocalDate dataInicio;
	private ProfessorResponseDto professorResponsavel;
	private boolean temGrupo;

	public ProjetoResponseDto() {
	}

	public ProjetoResponseDto(Projeto projeto) {
		if (projeto != null) {
			this.id = projeto.getId();
			this.titulo = projeto.getTitulo();
			this.objetivo = projeto.getObjetivo();
			this.perfilUsuarios = projeto.getPerfilUsuarios();
			this.localUtilizacao = projeto.getLocalUtilizacao();
			this.funcionalidades = projeto.getFuncionalidades();
			this.demanda = projeto.getDemanda();
			this.dataInicio = projeto.getDataInicio();
			this.professorResponsavel = new ProfessorResponseDto(projeto.getProfessor(),
					projeto.getProfessor().getUsuario());
			this.temGrupo = projeto.isTemGrupo();
		}
	}

	public long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public String getPerfilUsuarios() {
		return perfilUsuarios;
	}

	public String getLocalUtilizacao() {
		return localUtilizacao;
	}

	public String getFuncionalidades() {
		return funcionalidades;
	}

	public String getDemanda() {
		return demanda;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public ProfessorResponseDto getProfessorResponsavel() {
		return professorResponsavel;
	}

	public boolean isTemGrupo() {
		return temGrupo;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public void setPerfilUsuarios(String perfilUsuarios) {
		this.perfilUsuarios = perfilUsuarios;
	}

	public void setLocalUtilizacao(String localUtilizacao) {
		this.localUtilizacao = localUtilizacao;
	}

	public void setFuncionalidades(String funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setProfessorResponsavel(ProfessorResponseDto professorResponsavel) {
		this.professorResponsavel = professorResponsavel;
	}

	public void setTemGrupo(boolean temGrupo) {
		this.temGrupo = temGrupo;
	}
}
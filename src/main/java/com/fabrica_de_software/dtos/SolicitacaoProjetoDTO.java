package com.fabrica_de_software.dtos;

import java.time.LocalDate;

public class SolicitacaoProjetoDTO {
	private String titulo;
	private String objetivo;
	private String perfilUsuarios;
	private String localUtilizacao;
	private String funcionalidades;
	private String demanda;
	private LocalDate dataInicio;
	private long professorResponsavelId;

	public SolicitacaoProjetoDTO() {

	}

	public SolicitacaoProjetoDTO(String titulo, String objetivo, String perfilUsuarios, String localUtilizacao,
			String funcionalidades, String demanda, LocalDate dataInicio, long professorResponsavelId) {
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.perfilUsuarios = perfilUsuarios;
		this.localUtilizacao = localUtilizacao;
		this.funcionalidades = funcionalidades;
		this.demanda = demanda;
		this.dataInicio = dataInicio;
		this.professorResponsavelId = professorResponsavelId;
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

	public long getProfessorResponsavelId() {
		return professorResponsavelId;
	}

	public void setProfessorResponsavelId(long professorResponsavelId) {
		this.professorResponsavelId = professorResponsavelId;
	}

}

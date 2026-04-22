package com.fabrica_de_software.dtos;

import java.time.LocalDate;

public class ProjetoDTO {
	private long id;
	private String titulo;
	private String objetivo;
	private String perfilUsuarios;
	private String localUtilizacao;
	private String funcionalidades;
	private String demanda;
	private LocalDate dataInicio;
	private ProfessorDTO professorResponsavel;
	private boolean temGrupo;

	public ProjetoDTO() {
	}

	private ProjetoDTO(Builder b) {
		this.id = b.id;
		this.titulo = b.titulo;
		this.objetivo = b.objetivo;
		this.perfilUsuarios = b.perfilUsuarios;
		this.localUtilizacao = b.localUtilizacao;
		this.funcionalidades = b.funcionalidades;
		this.demanda = b.demanda;
		this.dataInicio = b.dataInicio;
		this.professorResponsavel = b.professorResponsavel;
		this.temGrupo = b.temGrupo;
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

	public ProfessorDTO getProfessorResponsavel() {
		return professorResponsavel;
	}

	public boolean isTemGrupo() {
		return temGrupo;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private long id;
		private String titulo;
		private String objetivo;
		private String perfilUsuarios;
		private String localUtilizacao;
		private String funcionalidades;
		private String demanda;
		private LocalDate dataInicio;
		private ProfessorDTO professorResponsavel;
		private boolean temGrupo;

		public Builder() {
		}

		public Builder id(long id) {
			this.id = id;
			return this;
		}

		public Builder titulo(String titulo) {
			this.titulo = titulo;
			return this;
		}

		public Builder objetivo(String objetivo) {
			this.objetivo = objetivo;
			return this;
		}

		public Builder perfilUsuarios(String perfilUsuarios) {
			this.perfilUsuarios = perfilUsuarios;
			return this;
		}

		public Builder localUtilizacao(String localUtilizacao) {
			this.localUtilizacao = localUtilizacao;
			return this;
		}

		public Builder funcionalidades(String funcionalidades) {
			this.funcionalidades = funcionalidades;
			return this;
		}

		public Builder demanda(String demanda) {
			this.demanda = demanda;
			return this;
		}

		public Builder dataInicio(LocalDate dataInicio) {
			this.dataInicio = dataInicio;
			return this;
		}

		public Builder professorResponsavel(ProfessorDTO professorResponsavel) {
			this.professorResponsavel = professorResponsavel;
			return this;
		}

		public Builder temGrupo(boolean temGrupo) {
			this.temGrupo = temGrupo;
			return this;
		}

		public ProjetoDTO build() {
			return new ProjetoDTO(this);
		}

	}

}

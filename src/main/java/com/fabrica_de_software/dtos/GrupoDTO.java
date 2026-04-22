package com.fabrica_de_software.dtos;

import java.util.List;

public class GrupoDTO {
	private long id;
	private ProjetoDTO projeto;
	private ProfessorDTO professorCoordenador;
	private List<AlunoDTO> alunos;

	public GrupoDTO() {
	}

	public GrupoDTO(long id, ProjetoDTO projeto, ProfessorDTO professorCoordenador, List<AlunoDTO> alunos) {
		this.id = id;
		this.projeto = projeto;
		this.professorCoordenador = professorCoordenador;
		this.alunos = alunos;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProjetoDTO getProjeto() {
		return projeto;
	}

	public void setProjeto(ProjetoDTO projeto) {
		this.projeto = projeto;
	}

	public ProfessorDTO getProfessorCoordenador() {
		return professorCoordenador;
	}

	public void setProfessorCoordenador(ProfessorDTO professorCoordenador) {
		this.professorCoordenador = professorCoordenador;
	}

	public List<AlunoDTO> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoDTO> alunos) {
		this.alunos = alunos;
	}

}

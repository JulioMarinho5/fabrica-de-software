package com.fabrica_de_software.dtos;

import java.util.List;

public class CadastroGrupoDTO {
	long projetoId;
	String professorCoordenadorEmail;
	List<Long> alunosIds;

	public CadastroGrupoDTO() {
	}

	public CadastroGrupoDTO(long projetoId, String professorCoordenadorEmail, List<Long> alunosIds) {
		this.projetoId = projetoId;
		this.professorCoordenadorEmail = professorCoordenadorEmail;
		this.alunosIds = alunosIds;
	}

	public long getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(long projetoId) {
		this.projetoId = projetoId;
	}

	public String getProfessorCoordenadorEmail() {
		return professorCoordenadorEmail;
	}

	public void setProfessorCoordenadorEmail(String professorCoordenadorEmail) {
		this.professorCoordenadorEmail = professorCoordenadorEmail;
	}

	public List<Long> getAlunosIds() {
		return alunosIds;
	}

	public void setAlunosIds(List<Long> alunosIds) {
		this.alunosIds = alunosIds;
	}

}

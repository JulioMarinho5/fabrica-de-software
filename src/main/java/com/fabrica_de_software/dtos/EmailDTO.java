package com.fabrica_de_software.dtos;

import java.util.List;

public class EmailDTO {
	private String email;
	private String ra;
	private String tipoEvento;
	List<AlunoDTO> alunos;

	public EmailDTO() {
	}

	public EmailDTO(String email, String ra, String tipoEvento, List<AlunoDTO> alunos) {
		this.email = email;
		this.ra = ra;
		this.tipoEvento = tipoEvento;
		this.alunos = alunos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<AlunoDTO> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoDTO> alunos) {
		this.alunos = alunos;
	}

}

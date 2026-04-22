package com.fabrica_de_software.dtos;

import com.fabrica_de_software.enums.StatusProjeto;

public class StatusProjetoDTO {
	private long projetoId;
	private StatusProjeto status;

	public StatusProjetoDTO() {
	}

	public StatusProjetoDTO(long projetoId, StatusProjeto status) {
		this.projetoId = projetoId;
		this.status = status;
	}

	public long getProjetoId() {
		return projetoId;
	}

	public void setProjetoId(long projetoId) {
		this.projetoId = projetoId;
	}

	public StatusProjeto getStatus() {
		return status;
	}

	public void setStatus(StatusProjeto status) {
		this.status = status;
	}

}

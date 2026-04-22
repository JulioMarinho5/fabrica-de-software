package com.fabrica_de_software.dtos;

import java.time.LocalDateTime;

public class MensagemDTO {
	private String mensagem;
	private LocalDateTime horarioCadastro;

	public MensagemDTO() {
	}

	public MensagemDTO(String mensagem, LocalDateTime horarioCadastro) {
		this.mensagem = mensagem;
		this.horarioCadastro = horarioCadastro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getHorarioCadastro() {
		return horarioCadastro;
	}

	public void setHorarioCadastro(LocalDateTime horarioCadastro) {
		this.horarioCadastro = horarioCadastro;
	}

}

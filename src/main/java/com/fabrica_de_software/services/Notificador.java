package com.fabrica_de_software.services;

import com.fabrica_de_software.dtos.EmailDto;

public interface Notificador {
	public void enviarEmailEmAnalise(String emailProfessor);

	public void enviarEmailAprovacao(String emailProfessor);

	public void enviarEmailCancelamento(String emailProfessor);

	public void enviarEmailCadastro(EmailDto dto);

	public void enviarEmailProfessorNovoGrupo(EmailDto dto);

	public void enviarEmailAlunoNovoGrupo(EmailDto dto);

}

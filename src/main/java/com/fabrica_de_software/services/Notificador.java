package com.fabrica_de_software.services;

import com.fabrica_de_software.dtos.EmailDTO;

public interface Notificador {
	public void enviarEmailEmAnalise(String emailProfessor);

	public void enviarEmailAprovacao(String emailProfessor);

	public void enviarEmailCancelamento(String emailProfessor);

	public void enviarEmailCadastro(EmailDTO dto);

	public void enviarEmailNovoGrupo(EmailDTO dto);

}

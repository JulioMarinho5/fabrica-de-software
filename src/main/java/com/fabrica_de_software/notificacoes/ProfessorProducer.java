package com.fabrica_de_software.notificacoes;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.EmailDto;

@Service
public class ProfessorProducer {
	private RabbitTemplate rabbitTemplate;

	public ProfessorProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void enviarEmailEmAnalise(String emailProfessor) {
		rabbitTemplate.convertAndSend("fila.email.professor", new EmailDto(emailProfessor, null, "ANALISE", null));
	}

	public void enviarEmailAprovacao(String emailProfessor) {
		rabbitTemplate.convertAndSend("fila.email.professor", new EmailDto(emailProfessor, null, "APROVACAO", null));
	}

	public void enviarEmailCancelamento(String emailProfessor) {
		rabbitTemplate.convertAndSend("fila.email.professor", new EmailDto(emailProfessor, null, "CANCELAMENTO", null));
	}

	public void enviarEmailCadastro(String emailProfessor, String ra) {
		rabbitTemplate.convertAndSend("fila.email.professor", new EmailDto(emailProfessor, ra, "CADASTRO", null));
	}

	public void enviarEmailGrupo(String emailProfessor, List<AlunoResponseDto> alunos) {
		rabbitTemplate.convertAndSend("fila.email.professor",
				new EmailDto(emailProfessor, null, "GRUPO_CRIADO", alunos));
	}

}

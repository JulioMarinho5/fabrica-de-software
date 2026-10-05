package com.fabrica_de_software.notificacoes;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.EmailDto;

@Service
public class AlunoProducer {
	private RabbitTemplate rabbitTemplate;

	public AlunoProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void enviarEmailCadastro(String emailAluno, String ra) {
		rabbitTemplate.convertAndSend("fila.email.aluno", new EmailDto(emailAluno, ra, "CADASTRO", null));
	}

	public void enviarEmailGrupo(String emailAluno, List<AlunoResponseDto> alunos) {
		rabbitTemplate.convertAndSend("fila.email.aluno", new EmailDto(emailAluno, null, "GRUPO_CRIADO", alunos));
	}

}

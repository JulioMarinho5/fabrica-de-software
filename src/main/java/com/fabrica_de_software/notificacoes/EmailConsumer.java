package com.fabrica_de_software.notificacoes;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.EmailDto;
import com.fabrica_de_software.services.EmailService;

@Service
public class EmailConsumer {
	private EmailService emailService;

	public EmailConsumer(EmailService emailService) {
		this.emailService = emailService;
	}

	@RabbitListener(queues = "fila.email.professor")
	public void processarFilaEmail(EmailDto dto) {
		switch (dto.tipoEvento()) {
		case "ANALISE" -> emailService.enviarEmailEmAnalise(dto.email());
		case "APROVACAO" -> emailService.enviarEmailAprovacao(dto.email());
		case "CANCELAMENTO" -> emailService.enviarEmailCancelamento(dto.email());
		case "CADASTRO" -> emailService.enviarEmailCadastro(dto);
		case "GRUPO_CRIADO" -> emailService.enviarEmailNovoGrupo(dto);
		default -> System.out.println("Tipo de e-mail desconhecido: " + dto.tipoEvento());
		}
	}

}

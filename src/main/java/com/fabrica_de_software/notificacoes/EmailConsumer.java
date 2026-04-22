package com.fabrica_de_software.notificacoes;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.EmailDTO;
import com.fabrica_de_software.services.EmailService;

@Service
public class EmailConsumer {
	private EmailService emailService;

	public EmailConsumer(EmailService emailService) {
		this.emailService = emailService;
	}

	@RabbitListener(queues = "fila.email")
	public void processarFilaEmail(EmailDTO dto) {
		switch (dto.getTipoEvento()) {
		case "ANALISE" -> emailService.enviarEmailEmAnalise(dto.getEmail());
		case "APROVACAO" -> emailService.enviarEmailAprovacao(dto.getEmail());
		case "CANCELAMENTO" -> emailService.enviarEmailCancelamento(dto.getEmail());
		case "CADASTRO" -> emailService.enviarEmailCadastro(dto);
		case "GRUPO_CRIADO" -> emailService.enviarEmailNovoGrupo(dto);
		default -> System.out.println("Tipo de e-mail desconhecido: " + dto.getTipoEvento());
		}
	}

}

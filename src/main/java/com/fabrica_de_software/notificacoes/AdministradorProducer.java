package com.fabrica_de_software.notificacoes;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fabrica_de_software.dtos.EmailDto;

@Service
public class AdministradorProducer {
	private RabbitTemplate rabbitTemplate;

	public AdministradorProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void enviarEmailCadastro(String emailAdm, String ra) {
		rabbitTemplate.convertAndSend("fila.email.adm", new EmailDto(emailAdm, ra, "CADASTRO", null));
	}

}

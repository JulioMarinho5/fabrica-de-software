package com.fabrica_de_software.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue filaEmailProf() {
		return new Queue("fila.email.professor", true); // true = durável
	}

	@Bean
	public Queue filaEmailAdm() {
		return new Queue("fila.email.adm", true); // true = durável
	}

	@Bean
	public Queue filaEmailAluno() {
		return new Queue("fila.email.aluno", true); // true = durável
	}
}

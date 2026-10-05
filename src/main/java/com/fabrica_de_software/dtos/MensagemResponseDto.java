package com.fabrica_de_software.dtos;

import java.time.LocalDateTime;

public record MensagemResponseDto(String mensagem, LocalDateTime horarioCadastro) {

}

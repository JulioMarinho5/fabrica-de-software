package com.fabrica_de_software.dtos;

import java.util.List;

public record EmailDto(String email, String ra, String tipoEvento, List<AlunoResponseDto> alunos) {
}

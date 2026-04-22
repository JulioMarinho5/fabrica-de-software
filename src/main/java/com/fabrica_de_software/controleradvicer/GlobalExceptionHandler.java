package com.fabrica_de_software.controleradvicer;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fabrica_de_software.exceptions.AlunoJaCadastradoEmGrupoException;
import com.fabrica_de_software.exceptions.AlunoJaCadastradoException;
import com.fabrica_de_software.exceptions.AlunoNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProfessorJaCadastradoException;
import com.fabrica_de_software.exceptions.ProfessorNaoEncontradoException;
import com.fabrica_de_software.exceptions.ProjetoNaoEncontradoException;
import com.fabrica_de_software.exceptions.SenhaIncorretaException;
import com.fabrica_de_software.exceptions.TransacaoInvalidaException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProfessorJaCadastradoException.class)
	public ResponseEntity<Map<String, String>> professorJaCadastrado(ProfessorJaCadastradoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(AlunoJaCadastradoException.class)
	public ResponseEntity<Map<String, String>> alunoJaCadastrado(AlunoJaCadastradoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(ProfessorNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> professorNaoEncontrado(ProfessorNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(ProjetoNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> projetoNaoEncontrado(ProjetoNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(TransacaoInvalidaException.class)
	public ResponseEntity<Map<String, String>> transacaoInvalida(TransacaoInvalidaException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(SenhaIncorretaException.class)
	public ResponseEntity<Map<String, String>> senhaIncorreta(SenhaIncorretaException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(AlunoNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> alunoNaoEncontrado(AlunoNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(AlunoJaCadastradoEmGrupoException.class)
	public ResponseEntity<Map<String, String>> alunoJaCadastrado(AlunoJaCadastradoEmGrupoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

}

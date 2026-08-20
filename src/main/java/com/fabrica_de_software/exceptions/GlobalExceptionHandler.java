package com.fabrica_de_software.exceptions;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, String>> tokenInvalido(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(GithubUrlJaExistenteException.class)
	public ResponseEntity<Map<String, String>> githubUrlInvalida(GithubUrlJaExistenteException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(LinkedinUrlJaExistenteException.class)
	public ResponseEntity<Map<String, String>> linkedinUrlInvalida(LinkedinUrlJaExistenteException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> dadosDuplicados(DataIntegrityViolationException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

}

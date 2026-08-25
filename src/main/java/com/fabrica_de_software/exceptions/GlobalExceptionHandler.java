package com.fabrica_de_software.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Map<String, String>> erroDeAutenticacao(AuthenticationException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(Map.of("erro", "Credenciais inválidas. Verifique seu usuário e senha."));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, String>> credenciaisInvalidas(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "Usuário ou senha incorretos."));
	}

	@ExceptionHandler(LoginInvalidoException.class)
	public ResponseEntity<Map<String, String>> loginInvalido(LoginInvalidoException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(AdministradorJaCadastradoException.class)
	public ResponseEntity<Map<String, String>> admJaCadastrado(AdministradorJaCadastradoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(ProfessorJaCadastradoException.class)
	public ResponseEntity<Map<String, String>> professorJaCadastrado(ProfessorJaCadastradoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(AlunoJaCadastradoException.class)
	public ResponseEntity<Map<String, String>> alunoJaCadastrado(AlunoJaCadastradoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(AlunoJaCadastradoEmGrupoException.class)
	public ResponseEntity<Map<String, String>> alunoJaCadastradoEmGrupo(AlunoJaCadastradoEmGrupoException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
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
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(Map.of("erro", "Violação de integridade nos dados. Registro duplicado ou inválido."));
	}

	@ExceptionHandler(AdministradorNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> admNaoEncontrado(AdministradorNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(ProfessorNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> professorNaoEncontrado(ProfessorNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(ProjetoNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> projetoNaoEncontrado(ProjetoNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(AlunoNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> alunoNaoEncontrado(AlunoNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(UsuarioNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> usuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(RoleNaoEncontradoException.class)
	public ResponseEntity<Map<String, String>> roleNaoEncontrado(RoleNaoEncontradoException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
	}

	@ExceptionHandler(TransacaoInvalidaException.class)
	public ResponseEntity<Map<String, String>> transacaoInvalida(TransacaoInvalidaException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
	}

}

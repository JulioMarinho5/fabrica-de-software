package com.fabrica_de_software.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}

		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.BAD_REQUEST.value());
		response.put("error", "Erro de Validação");
		response.put("messages", errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("status", HttpStatus.UNAUTHORIZED.value());
		response.put("error", "Não autorizado");
		response.put("message", "Credenciais inválidas. Verifique seu usuário e senha.");

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, String>> badCredentials(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "Usuário ou senha incorretos."));
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

	@ExceptionHandler(SenhaIncorretaException.class)
	public ResponseEntity<Map<String, String>> senhaIncorreta(SenhaIncorretaException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", ex.getMessage()));
	}
}

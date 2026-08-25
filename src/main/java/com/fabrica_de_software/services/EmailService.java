package com.fabrica_de_software.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.fabrica_de_software.dtos.AlunoResponseDto;
import com.fabrica_de_software.dtos.EmailDto;

@Service
public class EmailService implements Notificador {
	private final JavaMailSender javaMailSender;

	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void enviarEmailEmAnalise(String emailProfessor) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(emailProfessor);
		mail.setSubject("🔍 STATUS DO SEU PROJETO FOI ATUALIZADO!");
		StringBuilder sb = new StringBuilder();
		sb.append("Caro ").append(emailProfessor).append(",\n\n");
		sb.append(
				"Informamos que o projeto que você solicitou já foi recebido e está atualmente EM ANÁLISE pela nossa equipe administrativa.\n\n");
		sb.append("Próximos Passos:\n");
		sb.append("-------------------------------------------\n");
		sb.append("• Nossa equipe revisará os requisitos técnicos e a viabilidade do projeto.\n");
		sb.append("• Você receberá uma nova notificação assim que o status for alterado para Aprovado ou Rejeitado.\n");
		sb.append("-------------------------------------------\n\n");
		sb.append("Não é necessário responder a este e-mail no momento.\n\n");
		sb.append("Att,\n");
		sb.append("Fábrica de Software UCSAL");
		mail.setText(sb.toString());
		javaMailSender.send(mail);
	}

	@Override
	public void enviarEmailAprovacao(String emailProfessor) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(emailProfessor);
		mail.setSubject("✅ STATUS DO SEU PROJETO FOI ATUALIZADO!");
		StringBuilder sb = new StringBuilder();
		sb.append("Caro ").append(emailProfessor).append(",\n\n");
		sb.append("Informamos que o projeto que você solicitou foi APROVADO pelo Administrador.\n\n");
		sb.append("O que acontece agora?\n");
		sb.append("-------------------------------------------\n");
		sb.append(
				"O administrador entrará em contato em breve para alinhar os detalhes técnicos e prazos para a realização do projeto.\n");
		sb.append("-------------------------------------------\n\n");
		sb.append("Att,\n");
		sb.append("Fábrica de Software UCSAL");
		mail.setText(sb.toString());
		javaMailSender.send(mail);
	}

	@Override
	public void enviarEmailCancelamento(String emailProfessor) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(emailProfessor);
		mail.setSubject("❌ STATUS DO SEU PROJETO FOI ATUALIZADO!");
		StringBuilder sb = new StringBuilder();
		sb.append("Caro ").append(emailProfessor).append(",\n\n");
		sb.append("Informamos que o projeto que você solicitou foi REJEITADO pelo Administrador.\n\n");
		sb.append("Observação:\n");
		sb.append("-------------------------------------------\n");
		sb.append(
				"Você pode revisar os requisitos e solicitar um novo projeto a qualquer momento através do nosso portal.\n");
		sb.append("-------------------------------------------\n\n");
		sb.append("Att,\n");
		sb.append("Fábrica de Software UCSAL");
		mail.setText(sb.toString());
		javaMailSender.send(mail);
	}

	@Override
	public void enviarEmailCadastro(EmailDto dto) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(dto.email());
		mail.setSubject("🎉 CADASTRO REALIZADO COM SUCESSO!");
		StringBuilder sb = new StringBuilder();
		sb.append("Caro ").append(dto.email()).append(",\n\n");
		sb.append("Seja bem-vindo! Seu cadastro na Fábrica de Software foi realizado com sucesso.\n\n");
		sb.append("Suas credenciais de acesso:\n");
		sb.append("-------------------------------------------\n");
		sb.append("LOGIN: ").append(dto.email()).append("\n");
		sb.append("SENHA: ").append(dto.ra()).append(" (Seu RA)\n");
		sb.append("-------------------------------------------\n\n");
		sb.append("Att,\n");
		sb.append("Fábrica de Software UCSAL");
		mail.setText(sb.toString());
		javaMailSender.send(mail);
	}

	@Override
	public void enviarEmailProfessorNovoGrupo(EmailDto dto) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(dto.email());
		mail.setSubject("NOVO GRUPO DE PROJETO FORMADO!");
		StringBuilder sb = new StringBuilder();
		sb.append("Caro " + dto.email() + ", um novo grupo foi vinculado ao seu projeto.\n\n");
		sb.append("Abaixo estão os dados dos alunos integrantes:\n");
		sb.append("-------------------------------------------\n");

		for (AlunoResponseDto aluno : dto.alunos()) {
			sb.append("Nome: ").append(aluno.getNome()).append("\n");
			sb.append("Email: ").append(aluno.getEmail()).append("\n");
			sb.append("Telefone: ").append(aluno.getTelefone()).append("\n");
			sb.append("-------------------------------------------\n");
		}
		sb.append("\nAtt,\nFábrica de Software UCSAL");
		mail.setText(sb.toString());
		javaMailSender.send(mail);
	}

	@Override
	public void enviarEmailAlunoNovoGrupo(EmailDto dto) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(dto.email());
		mail.setSubject("SEU NOVO GRUPO DE PROJETO FOI FORMADO!");
		StringBuilder sb = new StringBuilder();
		sb.append("Caro " + dto.email() + ", você foi vinculado a um novo grupo.\n\n");
		sb.append("Abaixo estão os dados dos alunos integrantes:\n");
		sb.append("-------------------------------------------\n");

		for (AlunoResponseDto aluno : dto.alunos()) {
			sb.append("Nome: ").append(aluno.getNome()).append("\n");
			sb.append("Email: ").append(aluno.getEmail()).append("\n");
			sb.append("Telefone: ").append(aluno.getTelefone()).append("\n");
			sb.append("-------------------------------------------\n");
		}
		sb.append("\nAtt,\nFábrica de Software UCSAL");
		mail.setText(sb.toString());
		javaMailSender.send(mail);
		
	}
	
	

}

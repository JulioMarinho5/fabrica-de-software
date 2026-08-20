package com.fabrica_de_software.entities;

import java.time.LocalDate;
import java.util.Set;

import com.fabrica_de_software.enums.Status;
import com.fabrica_de_software.enums.Turno;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alunos")
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true, columnDefinition = "CHAR(6)")
	private String ra;
	@Column(nullable = false, length = 150)
	private String curso;
	@Column(nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private Turno turno;
	@Column(nullable = false, name = "horas_semanais")
	private int horasSemanais;
	@Column(nullable = true, length = 255, name = "github_url", unique = true)
	private String githubUrl;
	@Column(nullable = true, length = 255, name = "linkedin_url", unique = true)
	private String linkedinUrl;
	@Column(nullable = false, name = "data_selecao")
	private LocalDate dataSelecao;
	@Column(nullable = false, name = "data_cadastro")
	private LocalDate dataCadastro;
	@ManyToOne
	@JoinColumn(name = "grupo_id", nullable = true)
	private Grupo grupo;
	@Column(nullable = true, length = 20)
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;
	@ManyToMany
	@JoinTable(name = "areas_interesse_alunos", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "area_id"))
	private Set<Area> areas;
	@ManyToMany
	@JoinTable(name = "disciplinas_aprovadas_alunos", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
	private Set<Disciplina> disciplinas;

	public Aluno() {
	}

	public Aluno(String ra, String curso, Turno turno, int horasSemanais, String githubUrl, String linkedinUrl,
			LocalDate dataSelecao, LocalDate dataCadastro, Grupo grupo, Status status, Usuario usuario) {
		this.ra = ra;
		this.curso = curso;
		this.turno = turno;
		this.horasSemanais = horasSemanais;
		this.githubUrl = githubUrl;
		this.linkedinUrl = linkedinUrl;
		this.dataSelecao = dataSelecao;
		this.dataCadastro = dataCadastro;
		this.grupo = grupo;
		this.status = status;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRa() {
		return ra;
	}

	public void setRa(String ra) {
		this.ra = ra;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public int getHorasSemanais() {
		return horasSemanais;
	}

	public void setHorasSemanais(int horasSemanais) {
		this.horasSemanais = horasSemanais;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public LocalDate getDataSelecao() {
		return dataSelecao;
	}

	public void setDataSelecao(LocalDate dataSelecao) {
		this.dataSelecao = dataSelecao;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Set<Area> getAreas() {
		return areas;
	}

	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

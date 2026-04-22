package com.fabrica_de_software.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GeradorDeRaService {

	public String gerarRaProfessor() {
		int numeroAleatorio = new Random().nextInt(10000, 100000);
		return String.valueOf(numeroAleatorio);
	}

	public String gerarRaAluno() {
		int numeroAleatorio = new Random().nextInt(100000, 1000000);
		return String.valueOf(numeroAleatorio);
	}

}

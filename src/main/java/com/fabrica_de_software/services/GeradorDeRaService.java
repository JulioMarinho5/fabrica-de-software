package com.fabrica_de_software.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class GeradorDeRaService {

	public String gerarRa() {
		int numeroAleatorio = new Random().nextInt(1000000);
		return String.format("%06d", numeroAleatorio);
	}

}

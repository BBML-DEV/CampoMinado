package br.com.bbml.cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.bbml.cm.excesao.ExplosaoException;
import br.com.bbml.cm.excesao.SairException;
import br.com.bbml.cm.model.Table;
//import jdk.internal.misc.FileSystemOption;

public class TableTerminal {

	private Table table;
	private Scanner entrada = new Scanner(System.in);
	
	public TableTerminal(Table table) {
		this.table = table;
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				cicloDojogo();
				
				System.out.println("Quer jogar outra partida ? (S/n): ");
				String resposta = entrada.nextLine();
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				} else {
					table.reiniciar();
				}
			}
		} catch (SairException e) {
			System.out.println("O jogo acabou, até breve!");
		} finally {
			entrada.close();
		}
	}

	private void cicloDojogo() {
		try {
			
			while(!table.objetivoAlcancado()) {
				System.out.println(table);
				
				String digitado = capturarValorDigitado("Digite (x, y): ");
				
				
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
				.map(e -> Integer.parseInt(e.trim())).iterator();
				
				digitado = capturarValorDigitado("1- abrir ou 2 - (Des)Marcar: ");
				
				if("1".equals(digitado)) {
					table.abrir(xy.next(), xy.next()); 
				} else if ("2".equals(digitado)) {
					table.alternarMarcacao(xy.next(), xy.next());
				}
			}
			
			System.out.println("Você ganhou!");
		} catch (ExplosaoException e) {
			System.out.println(table);
			System.out.println("Você perdeu o jogo!");
		}
	}
	
	private String capturarValorDigitado(String texto) {
		System.out.print(texto);
		String digitado = entrada.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		
		return digitado;
	}

	
}

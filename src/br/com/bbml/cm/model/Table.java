package br.com.bbml.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.bbml.cm.excesao.ExplosaoException;

public class Table {
	
	
	//atributos
	private int quantidadeLinhas;
	private int quantidadeColunas;
	private int quantidadeMinas;
	
	private final List<Field> field = new ArrayList<>();
	
	//método construtor
	public Table(int quantidadeLinhas, int quantidadeColunas, int quantidadeMinas) {
		this.quantidadeLinhas = quantidadeLinhas;
		this.quantidadeColunas = quantidadeColunas;
		this.quantidadeMinas = quantidadeMinas;
		generateField();
		associarOsVizinhos();
		sortearMinas();
	} 
	
	public void abrir(int linha, int coluna) {
		try {
			field.parallelStream()
			.filter(f -> f.getLinha() == linha && f.getColuna() == coluna)
			.findFirst()
			.ifPresent(f -> f.abrir());
		} catch (ExplosaoException e) {
			field.forEach(f -> f.setAberto(true));
			throw e;
		}
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		field.parallelStream()
		.filter(f -> f.getLinha() == linha && f.getColuna() == coluna)
		.findFirst()
		.ifPresent(f -> f.alternarMarcacao());
	}


	private void generateField() {
		for (int qtlinha = 0; qtlinha < quantidadeLinhas; qtlinha++) {
			for (int qtcolunas = 0; qtcolunas < quantidadeColunas; qtcolunas++) {
				field.add(new Field(qtcolunas, qtlinha));
			}
		}
	
	}
	
	private void associarOsVizinhos() {
		for(Field c1: field) {
			for(Field c2: field) {
				c1.adicionarVizinho(c2);
			}
		}
		
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Field> minado =  f -> f.isMinado();
		
		do {
			int aleatorio = (int) (Math.random()* field.size());
			field.get(aleatorio).minar();
			minasArmadas = field.stream().filter(minado).count();
		} while (minasArmadas < quantidadeMinas);
	}
	
	public boolean objetivoAlcancado() {
		return field.stream().allMatch(f -> f.objetivoAlcancado());
	}
	
	public void reiniciar() {
		field.stream().forEach(f -> f.reiniciar());
		sortearMinas();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(" ");
		for (int c = 0; c < quantidadeColunas; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		sb.append("\n");
		
		
		int i = 0;
		for (int l = 0; l < quantidadeLinhas; l++) {
			sb.append(l);
			sb.append("");
			for (int c = 0; c < quantidadeColunas; c++) {
				sb.append(" ");
				sb.append(field.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

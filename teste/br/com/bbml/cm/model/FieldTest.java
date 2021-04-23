package br.com.bbml.cm.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.bbml.cm.excesao.ExplosaoException;

public class FieldTest {

	
	private Field field;
	
	@BeforeEach
	void inciarCampo() { //função pra inciiar o campo
		field = new Field(3,3);
	}
	
	//Funções de teste para vizinhos nas verticais e horizontais
	@Test
	void testeVizinhoDistancia1Esquerda() {
		Field vizinho = new Field(3,2);
		boolean resultado = field.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1Direita() {
		Field vizinho = new Field(3,4);
		boolean resultado = field.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1EmCima() {
		Field vizinho = new Field(2,3);
		boolean resultado = field.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	@Test
	void testeVizinhoDistancia1EmBaixo() {
		Field vizinho = new Field(4,3);
		boolean resultado = field.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	//Função para teste de vizinhos nas diagonais
	@Test
	void testeVizinhoRealDistanciaDiagonal() {
		Field vizinho = new Field(2,2);
		boolean resultado = field.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	
	//Função para teste de !vizinho
	@Test
	void testeNaoVizinho() {
		Field vizinho = new Field(1,1);
		boolean resultado = field.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	
	//função testar alternar marcação
	@Test
	void testeValorPadraoAtributoMarcado() {
		assertFalse(field.isMarcado());
	}
	
	
	@Test
	void testeAlternarMarcacao() {
		field.alternarMarcacao();
		assertTrue(field.isMarcado());
	}
	
	@Test
	void testeAlternarMarcacaoDuasChamadas() {
		field.alternarMarcacao();
		field.alternarMarcacao();
		assertFalse(field.isMarcado());
	}
	
	//Função teste para abrir o campo
	@Test
	void testeAbrirNaoMinadoNaoMarcado() {
		assertTrue(field.abrir());
	}
	
	@Test
	void testeAbrirNaoMinadoMarcado() {
		field.alternarMarcacao();
		assertFalse(field.abrir());
	}
	
	@Test
	void testeAbrirMinadoMarcado() {
		field.alternarMarcacao();
		field.minar();
		assertFalse(field.abrir());
	}
	@Test
	void testeAbrirMinadoNaoMarcado() {
		field.minar();
		assertThrows(ExplosaoException.class, () -> {
			field.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinhos1() {
		Field field11 = new Field(1, 1);
		Field field22 = new Field(2, 2);
		field22.adicionarVizinho(field11);

		
		field.adicionarVizinho(field22);
		field.abrir();

		
		assertTrue(field22.isAberto() && field11.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhos2() {
		Field field11 = new Field(1, 1);
		Field field12 = new Field(1, 1);
		field12.minar();
		
		Field field22 = new Field(2, 2);
		field22.adicionarVizinho(field11);
		field22.adicionarVizinho(field12);
		
		
		field.adicionarVizinho(field22);
		field.abrir();
		
		assertTrue(field22.isAberto() && field11.isFechado());
	}
}

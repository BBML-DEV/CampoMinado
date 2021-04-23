package br.com.bbml.cm;

import br.com.bbml.cm.model.Table;
import br.com.bbml.cm.view.TableTerminal;

public class Aplicacao {

	public static void main(String[] args) {
		
		Table table = new Table(6, 6, 3);
		new TableTerminal(table);
	
	}
	
}

package br.com.perfin.model;

import android.content.ContentValues;

public class Renda {
	
	private int id;
	private Entrada entrada;
	
	
	public Renda(int id, Entrada entrada) {		
		super();
		this.id = id;
		this.entrada = entrada;
	}
	
	public Renda(Entrada entrada) {				
		super();
		this.entrada = entrada;
	}
	
	public Renda() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	
	/** Monta o MAP para o DAO **/
	public ContentValues toBD(){
		ContentValues valores = new ContentValues();
		valores.put("_id", this.id);
		valores.put("entradaID", this.entrada.getId());
		return valores;
	}

}

package br.com.perfin.model;

import android.content.ContentValues;

public class Categoria {
	
	private int id;
	private String descricao;
	
	
	public Categoria(int id, String descricao) {		
		super();
		this.id = id;
		this.descricao = descricao;
	}
	
	public Categoria(String descricao) {		
		super();
		this.descricao = descricao;
	}
	
	public Categoria() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/** Monta o MAP para o DAO **/
	public ContentValues toBD(){
		ContentValues valores = new ContentValues();
		valores.put("_id", this.id);
		valores.put("descricao", this.descricao);
		return valores;
	}

}

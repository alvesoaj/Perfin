package br.com.perfin.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;

public class Entrada {
	
	private int id;
	private Date data;
	private float valor;
	private String descricao;
	private Categoria categoria;
	
	
	public Entrada(int id, Date data, float valor, String descricao, Categoria categoria) {		
		super();
		this.id = id;
		this.data = data;
		this.valor = valor;
		this.descricao = descricao;
		this.categoria = categoria;
	}
	
	public Entrada(Date data, float valor, String descricao, Categoria categoria) {		
		super();
		this.data = data;
		this.valor = valor;
		this.descricao = descricao;
		this.categoria = categoria;
	}
	
	public Entrada(float valor, String descricao, Categoria categoria) {		
		super();
		this.valor = valor;
		this.descricao = descricao;
		this.categoria = categoria;
	}
	
	public Entrada(float valor, Categoria categoria) {		
		super();
		this.valor = valor;
		this.categoria = categoria;
	}
	
	public Entrada() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/** Monta o MAP para o DAO **/
	public ContentValues toBD(){
		ContentValues valores = new ContentValues();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		valores.put("_id", this.id);
		valores.put("data", sdf.format(this.data));
		valores.put("valor", this.valor);
		valores.put("descricao", this.descricao);
		valores.put("CategoriaID", this.categoria.getId());
		return valores;
	}

}

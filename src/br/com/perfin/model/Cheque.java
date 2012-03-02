package br.com.perfin.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;

public class Cheque {
	
	private int id;
	private Date data_pg;
	private String status;
	private Entrada entrada;
	
	
	public Cheque(int id, Date data_pg, String status, Entrada entrada) {		
		super();
		this.id = id;
		this.data_pg = data_pg;
		this.status = status;
		this.entrada = entrada;
	}
	
	public Cheque(Date data_pg, String status, Entrada entrada) {				
		super();
		this.data_pg = data_pg;
		this.status = status;
		this.entrada = entrada;
	}
	
	public Cheque() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getData_pg() {
		return data_pg;
	}

	public void setData_pg(Date data_pg) {
		this.data_pg = data_pg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		valores.put("_id", this.id);
		valores.put("data_pg", sdf.format(this.data_pg));
		valores.put("status", this.status);
		valores.put("entradaID", this.entrada.getId());
		return valores;
	}

}

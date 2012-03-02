package br.com.perfin.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;

public class Cartao {
	
	private int id;
	private int parcela;
	private Date data_faturamento;
	private Entrada entrada;
	
	
	public Cartao(int id, int parcela, Date data_faturamento, Entrada entrada) {		
		super();
		this.id = id;
		this.parcela = parcela;
		this.data_faturamento = data_faturamento;
		this.entrada = entrada;
	}
	
	public Cartao(int id, int parcela, Entrada entrada) {		
		super();
		this.id = id;
		this.parcela = parcela;
		this.entrada = entrada;
	}
	
	public Cartao(int parcela, Date data_faturamento, Entrada entrada) {		
		super();
		this.parcela = parcela;
		this.data_faturamento = data_faturamento;
		this.entrada = entrada;
	}
	
	public Cartao(int parcela, Entrada entrada) {				
		super();
		this.parcela = parcela;
		this.entrada = entrada;
	}
	
	public Cartao() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public Date getDataFaturamento() {
		return data_faturamento;
	}

	public void setDataFaturamento(Date data_faturamento) {
		this.data_faturamento = data_faturamento;
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
		valores.put("parcelas", this.parcela);
		
		if(this.data_faturamento != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			valores.put("datafaturamento", sdf.format(this.data_faturamento));
		}
		
		valores.put("entradaID", this.entrada.getId());
		return valores;
	}

}

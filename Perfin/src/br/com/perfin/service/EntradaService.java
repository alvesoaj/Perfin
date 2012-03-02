package br.com.perfin.service;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.dao.EntradaDAO;
import br.com.perfin.model.Entrada;

public class EntradaService {
	private EntradaDAO entradaDAO;
	
	public EntradaService(SQLiteDatabase conexao) {
		this.entradaDAO = new EntradaDAO(conexao);
	}
	
	public Entrada salvar(Entrada entrada){
		return entradaDAO.salvar(entrada);
	}
	
	public void excluir(int entradaID){
		entradaDAO.excluir(entradaID);
	}
	
	public Entrada buscarPorID(int entradaID){
		return entradaDAO.buscarPorID(entradaID);
	}
	
	public List<Entrada> listar(Date data, float valor, String descricao, String status, int categoriaID){
		return entradaDAO.listar(data, valor, descricao, status, categoriaID);
	}
	public List<Entrada> listar(){
		return entradaDAO.listar();
	}
}

package br.com.perfin.service;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.dao.DinheiroDAO;
import br.com.perfin.model.Dinheiro;

public class DinheiroService {
	private DinheiroDAO dinheiroDAO;
	
	public DinheiroService(SQLiteDatabase conexao) {
		this.dinheiroDAO = new DinheiroDAO(conexao);
	}
	
	public Dinheiro salvar(Dinheiro dinheiro){
		return dinheiroDAO.salvar(dinheiro);
	}
	
	public void excluir(int dinheiroID){
		dinheiroDAO.excluir(dinheiroID);
	}
	
	public Dinheiro buscarPorID(int dinheiroID){
		return dinheiroDAO.buscarPorID(dinheiroID);
	}
	
	public List<Dinheiro> listar(int entrada_id){
		return dinheiroDAO.listar(entrada_id);
	}
}

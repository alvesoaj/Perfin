package br.com.perfin.service;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.dao.RendaDAO;
import br.com.perfin.model.Renda;

public class RendaService {
	private RendaDAO rendaDAO;
	
	public RendaService(SQLiteDatabase conexao) {
		this.rendaDAO = new RendaDAO(conexao);
	}
	
	public Renda salvar(Renda renda){
		return rendaDAO.salvar(renda);
	}
	
	public void excluir(int rendaID){
		rendaDAO.excluir(rendaID);
	}
	
	public Renda buscarPorID(int rendaID){
		return rendaDAO.buscarPorID(rendaID);
	}
	
	public List<Renda> listar(int entrada_id){
		return rendaDAO.listar(entrada_id);
	}
}

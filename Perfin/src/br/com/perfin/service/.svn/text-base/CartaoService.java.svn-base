package br.com.perfin.service;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.dao.CartaoDAO;
import br.com.perfin.model.Cartao;

public class CartaoService {
	private CartaoDAO cartaoDAO;
	
	public CartaoService(SQLiteDatabase conexao) {
		this.cartaoDAO = new CartaoDAO(conexao);
	}
	
	public Cartao salvar(Cartao cartao){
		return cartaoDAO.salvar(cartao);
	}
	
	public void excluir(int cartaoID){
		cartaoDAO.excluir(cartaoID);
	}
	
	public Cartao buscarPorID(int cartaoID){
		return cartaoDAO.buscarPorID(cartaoID);
	}
	
	public List<Cartao> listar(int parcela, Date data, int entrada_id){
		return cartaoDAO.listar(parcela, data, entrada_id);
	}
}

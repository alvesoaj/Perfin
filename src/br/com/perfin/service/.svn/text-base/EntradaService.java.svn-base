package br.com.perfin.service;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.dao.EntradaDAO;
import br.com.perfin.model.Entrada;
import br.com.perfin.model.EntradaJoinAll;
import br.com.perfin.model.EntradaJoinCartao;
import br.com.perfin.model.EntradaJoinCheque;
import br.com.perfin.model.EntradaJoinDinheiro;
import br.com.perfin.model.EntradaJoinRenda;

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
	
	public List<Entrada> listar(Date data, float valor, String descricao, int categoriaID){
		return entradaDAO.listar(data, valor, descricao, categoriaID);
	}
	
	public List<EntradaJoinCartao> listar_cartao(String status, Date data_ini, Date data_fim){
		return entradaDAO.listar_cartao(status, data_ini, data_fim);
	}
	
	public List<EntradaJoinCheque> listar_cheque(String status, Date data_ini, Date data_fim){
		return entradaDAO.listar_cheque(status, data_ini, data_fim);
	}
	
	public List<EntradaJoinDinheiro> listar_dinheiro(Date data_ini, Date data_fim){
		return entradaDAO.listar_dinheiro(data_ini, data_fim);
	}
	
	public List<EntradaJoinRenda> listar_renda(Date data_ini, Date data_fim){
		return entradaDAO.listar_renda(data_ini, data_fim);
	}
	
	public List<EntradaJoinAll> listar_all(String status_card, String status_cheque, Date data_ini, Date data_fim){
		return entradaDAO.listar_all(status_card, status_cheque, data_ini, data_fim);
	}
	
	public List<Entrada> listar(){
		return entradaDAO.listar();
	}
}

package br.com.perfin.service;

import java.util.Date;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.dao.ChequeDAO;
import br.com.perfin.model.Cheque;

public class ChequeService {
	private ChequeDAO chequeDAO;
	
	public ChequeService(SQLiteDatabase conexao) {
		this.chequeDAO = new ChequeDAO(conexao);
	}
	
	public Cheque salvar(Cheque cheque){
		return chequeDAO.salvar(cheque);
	}
	
	public void excluir(int chequeID){
		chequeDAO.excluir(chequeID);
	}
	
	public Cheque buscarPorID(int chequeID){
		return chequeDAO.buscarPorID(chequeID);
	}
	
	public List<Cheque> listar(Date data_pg, String status, int entrada_id){
		return chequeDAO.listar(data_pg, status, entrada_id);
	}
	
	public List<Cheque> listarNP(Date data_pg, String status){
		return chequeDAO.listarNP(data_pg, status);
	}
}

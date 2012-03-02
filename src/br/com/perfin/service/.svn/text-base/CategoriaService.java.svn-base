package br.com.perfin.service;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.dao.CategoriaDAO;
import br.com.perfin.model.Categoria;

public class CategoriaService {
	private CategoriaDAO categoriaDAO;
	
	public CategoriaService(SQLiteDatabase conexao) {
		this.categoriaDAO = new CategoriaDAO(conexao);
	}
	
	public Categoria salvar(Categoria categoria){
		return categoriaDAO.salvar(categoria);
	}
	
	public void excluir(int categoriaID){
		categoriaDAO.excluir(categoriaID);
	}
	
	public Categoria buscarPorID(int categoriaID){
		return categoriaDAO.buscarPorID(categoriaID);
	}
	
	public List<Categoria> listar(String descricao){
		return categoriaDAO.listar(descricao);
	}
	
	public List<Categoria> listar(String descricao, String nao_descricao){
		return categoriaDAO.listar(descricao, nao_descricao);
	}
}

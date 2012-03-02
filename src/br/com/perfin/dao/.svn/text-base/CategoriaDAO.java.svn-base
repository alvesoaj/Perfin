package br.com.perfin.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.perfin.model.Categoria;

public class CategoriaDAO extends DAO{
	
	private SQLiteDatabase conexao;
	private String table = "categoria";
	private String[] columns = {"_id","descricao"};
	
	public CategoriaDAO(SQLiteDatabase conexao) {
		super(conexao);
		this.conexao = conexao;
	}

	public Categoria salvar(Categoria categoria){
		
		if(categoria.getId() > 0)
			conexao.update(table, categoria.toBD(), "_id = ?", new String[]{String.valueOf(categoria.getId())});
		else{
			categoria.setId(super.ultimoID(table));
			conexao.insert(table, null, categoria.toBD());
		}
		
		return categoria;
	}
	
	public void excluir(int categoriaID){
		conexao.delete(table, "_id = ?", new String[]{String.valueOf(categoriaID)});
	}
	
	public Categoria buscarPorID(int categoriaID){
		
		Cursor c = conexao.query(table, columns, "_id = ?", new String[]{String.valueOf(categoriaID)}, null, null, null);

		if(c.getCount() <= 0) return null;
		
		c.moveToFirst();
		
		int idxId = c.getColumnIndex("_id");
		int idxDescricao = c.getColumnIndex("descricao");
		
		Categoria categoria = new Categoria(c.getInt(idxId), c.getString(idxDescricao));
		
		return categoria;
	}
	
	public List<Categoria> listar(String descricao){
		
		String where = getWhere(descricao);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(descricao);
		}
		
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, null);
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		if(c.moveToFirst()){
		
			int idxId = c.getColumnIndex("_id");
			int idxDescricao = c.getColumnIndex("descricao");
			
			do {
				
				Categoria categoria = new Categoria(c.getInt(idxId), c.getString(idxDescricao));
				categorias.add(categoria);
				
			} while (c.moveToNext());
			
		}					
		
		return categorias;
	}
	
	public List<Categoria> listar(String descricao, String nao_descricao){
		
		String where = getWhere(descricao, nao_descricao);
		String[] whereArgs = null;
		
		if(where.length() > 0){
			whereArgs = getWhereArgs(descricao, nao_descricao);
		}
		
		Cursor c = conexao.query(table, columns, where, whereArgs, null, null, "descricao");
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		if(c.moveToFirst()){
		
			int idxId = c.getColumnIndex("_id");
			int idxDescricao = c.getColumnIndex("descricao");
			
			do {
				
				Categoria categoria = new Categoria(c.getInt(idxId), c.getString(idxDescricao));
				categorias.add(categoria);
				
			} while (c.moveToNext());
			
		}					
		
		return categorias;
	}
	
	private String getWhere(String descricao){
		String where = " 1 = 1 ";
		
		if(descricao.trim().length() > 0){
			//nome = nome.replaceAll("'", "\'");
			where += " AND descricao like ? ";
		}
		
		return where;
		
	}
	
	private String getWhere(String descricao, String nao_descricao){
		String where = " 1 = 1 ";
		
		if(descricao.trim().length() > 0){
			//nome = nome.replaceAll("'", "\'");
			where += " AND descricao like ? ";
		}
		
		if(nao_descricao.trim().length() > 0){
			//nome = nome.replaceAll("'", "\'");
			where += " AND descricao != ? ";
		}
		
		return where;
		
	}
	
	private String[] getWhereArgs(String descricao){
		
		List<String> args = new ArrayList<String>();
		
		if(descricao.trim().length() > 0){
			args.add("%"+descricao+"%");
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		return whereArgs;
		
	}
	
	private String[] getWhereArgs(String descricao, String nao_descricao){
		
		List<String> args = new ArrayList<String>();
		
		if(descricao.trim().length() > 0){
			args.add("%"+descricao+"%");
		}
		
		if(nao_descricao.trim().length() > 0){
			args.add(nao_descricao);
		}
		
		String[] whereArgs = new String[args.size()];
		
		for (int i = 0; i < args.size(); i++) {
			whereArgs[i] = args.get(i);			
		}
		
		return whereArgs;
		
	}
	
}
